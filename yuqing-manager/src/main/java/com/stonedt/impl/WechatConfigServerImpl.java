package com.stonedt.impl;

import com.stonedt.dao.WechatConfigDao;
import com.stonedt.entity.WechatConfig;
import com.stonedt.service.WechatConfigServer;
import com.stonedt.util.ResultUtil;
import com.stonedt.yuqingwechat.config.WechatConfigOperation;
import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 文轩
 */
public class WechatConfigServerImpl implements WechatConfigServer {


    private final WechatConfigOperation wechatConfigOperation;

    private final WechatConfigDao wechatConfigDao;

    public WechatConfigServerImpl(WechatConfigOperation wechatConfigOperation,
                                  WechatConfigDao wechatConfigDao) {
        this.wechatConfigOperation = wechatConfigOperation;
        this.wechatConfigDao = wechatConfigDao;
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
}
