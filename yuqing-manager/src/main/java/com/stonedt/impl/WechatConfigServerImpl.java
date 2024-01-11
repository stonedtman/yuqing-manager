package com.stonedt.impl;

import com.alibaba.fastjson2.JSON;
import com.stonedt.dao.WechatConfigDao;
import com.stonedt.entity.WechatConfig;
import com.stonedt.service.WechatConfigServer;
import com.stonedt.util.ResultUtil;
import com.stonedt.yuqingwechat.config.WechatConfigOperation;
import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 文轩
 */
public class WechatConfigServerImpl implements WechatConfigServer {


    private final WechatConfigOperation wechatConfigOperation;

    private final WechatConfigDao wechatConfigDao;

    private final RestTemplate restTemplate;

    public WechatConfigServerImpl(WechatConfigOperation wechatConfigOperation,
                                  WechatConfigDao wechatConfigDao,
                                  RestTemplate restTemplate) {
        this.wechatConfigOperation = wechatConfigOperation;
        this.wechatConfigDao = wechatConfigDao;
        this.restTemplate = restTemplate;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil<Void> updateConfig(WechatConfig wechatConfig, Boolean hasTest) {
        //构造配置对象
        WechatClientsProperties wechatClientsProperties = new WechatClientsProperties();
        wechatClientsProperties.setAppId(wechatConfig.getAppid());
        wechatClientsProperties.setSecret(wechatConfig.getSecret());
        wechatClientsProperties.setToken(wechatConfig.getToken());
        wechatClientsProperties.setTemplateId(wechatConfig.getTemplate_id());

        WechatClientsProperties.WechatClient wechatClient = new WechatClientsProperties.WechatClient();
        wechatClient.setPrefix("yuqing");

        String url = wechatConfig.getUrl();
        //去除末尾的/,如果有的话
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        String callback = wechatConfig.getCallback();
        if (callback.endsWith("/")) {
            callback = callback.substring(0, callback.length() - 1);
        }
        wechatClient.setCallback(callback + "/wechat");
        wechatClient.setSubscribe(url + "/wechat/handleSubscribe");
        wechatClient.setRedirect("https://gitee.com/stonedtx/yuqing");
        wechatClient.setUnsubscribe(url + "/wechat/handleUnsubscribe");
        wechatClient.setAuthorize(url + "/wechat/handleAuthorize");

        List<WechatClientsProperties.WechatClient> wechatClientList = new ArrayList<>();
        wechatClientList.add(wechatClient);

        wechatClientsProperties.setClients(wechatClientList);

        //测试
        if (Boolean.TRUE.equals(hasTest)) {
            boolean test = wechatConfigOperation.test(wechatClientsProperties);
            if (!test) {
                return ResultUtil.build(500, "校验失败!请检查您的配置是否正确");
            }
        }
        //查询原配置
        WechatConfig last = wechatConfigDao.selectLast();
        if (last != null && !wechatConfig.getAppid().equals(last.getAppid())) {
            //1.清空微信绑定
            wechatConfigDao.clearWechatBind();
            //2.更新用户微信状态
            wechatConfigDao.updateUserWechatStatus();
        }

        //存入数据库
        wechatConfigDao.update(wechatConfig);

        //重载
        wechatConfigOperation.reload(wechatClientsProperties);

        if (Boolean.TRUE.equals(hasTest)) {
            boolean refresh = wechatConfigOperation.refresh();
            if (!refresh) {
                return ResultUtil.build(500, "刷新失败!请检查您的配置是否正确");
            }
        }

        return ResultUtil.ok();
    }

    @Override
    public ResultUtil<WechatConfig> getConfig() {
        WechatConfig wechatConfig = wechatConfigDao.selectLast();

        return ResultUtil.ok(wechatConfig);
    }

    @Override
    public ResultUtil<String> getQrCode() {
        //构造sence
        String sceneStr = "yuqing:" + "#pass";

        try {
            String qrCode = wechatConfigOperation.getQrCode(sceneStr, 2592000);
            return ResultUtil.ok(qrCode);
        } catch (WxErrorException e) {
            return ResultUtil.build(500, "获取二维码失败",null);
        }
    }

    /**
     * 获取公众号名称
     */
    @Override
    public ResultUtil<String> getAccountName() {

        final String accessToken;
        try {
            accessToken = wechatConfigOperation.getAccessToken();
        } catch (WxErrorException e) {
            return ResultUtil.build(500, "获取公众号名称失败", null);
        }
        final String result;
        try {
            result = restTemplate.getForObject("https://api.weixin.qq.com/cgi-bin/account/getaccountbasicinfo?access_token=" + accessToken, String.class);
            System.out.println(result);
        } catch (RestClientException e) {
            return ResultUtil.build(500, "获取公众号名称失败", null);
        }

        String nickName = JSON.parseObject(result).getJSONObject("nickname_info").getString("nick_name");

        return ResultUtil.ok(nickName);
    }
}
