package com.stonedt.yuqingwechat.config;

import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import com.stonedt.yuqingwechat.wechat.WechatCallbackHandler;
import com.stonedt.yuqingwechat.wechat.WechatRedirectHandler;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信配置操作类
 * 对外暴露操作
 * @author 文轩
 */
@Slf4j
public class WechatConfigOperation {

    private final WechatRedirectHandler wechatRedirectHandler;

    private final WechatCallbackHandler wechatCallbackHandler;

    private final WxMpService wxMpService;

    private final WechatClientsProperties properties;


    public WechatConfigOperation(WechatRedirectHandler wechatRedirectHandler,
                                 WechatCallbackHandler wechatCallbackHandler,
                                 WxMpService wxMpService,
                                 WechatClientsProperties properties) {
        this.wechatRedirectHandler = wechatRedirectHandler;
        this.wechatCallbackHandler = wechatCallbackHandler;
        this.wxMpService = wxMpService;
        this.properties = properties;
    }


    /**
     * 配置重载
     */
    public Boolean reload(WechatClientsProperties wechatClientsProperties) {
        //参数重载
        properties.setAppId(wechatClientsProperties.getAppId());
        properties.setSecret(wechatClientsProperties.getSecret());
        properties.setToken(wechatClientsProperties.getToken());
        properties.setAesKey(wechatClientsProperties.getAesKey());
        properties.setTemplateId(wechatClientsProperties.getTemplateId());
        properties.setClients(wechatClientsProperties.getClients());

        boolean wxMpServiceReload = wxMpServiceReload(wechatClientsProperties);
        if (!wxMpServiceReload) {
            return false;
        }

        try {
            wechatCallbackHandler.reload(wechatClientsProperties);
            wechatRedirectHandler.reload(wechatClientsProperties);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 配置测试
     */
    public boolean test(WechatClientsProperties wechatClientsProperties) {
        //重载微信服务配置
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(wechatClientsProperties.getAppId());
        configStorage.setSecret(wechatClientsProperties.getSecret());
        configStorage.setToken(wechatClientsProperties.getToken());
        configStorage.setAesKey(wechatClientsProperties.getAesKey());

        Map<String, WxMpConfigStorage> wxMpConfigStorageMap = new HashMap<>();
        wxMpConfigStorageMap.put(wechatClientsProperties.getAppId(), configStorage);

        WxMpService service = new WxMpServiceImpl();
        service.setMultiConfigStorages(wxMpConfigStorageMap);

        //发送请求
        try {
            service.getAccessToken();
        } catch (WxErrorException e) {
            log.error("尝试请求token出错!错误信息:",e);
            return false;
        }
        return true;
    }

    /**
     * 重载微信服务配置
     */
    private boolean wxMpServiceReload(WechatClientsProperties wechatClientsProperties) {
        //重载微信服务配置
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(wechatClientsProperties.getAppId());
        configStorage.setSecret(wechatClientsProperties.getSecret());
        configStorage.setToken(wechatClientsProperties.getToken());
        configStorage.setAesKey(wechatClientsProperties.getAesKey());

        Map<String, WxMpConfigStorage> wxMpConfigStorageMap = new HashMap<>();
        wxMpConfigStorageMap.put(wechatClientsProperties.getAppId(), configStorage);

        wxMpService.setMultiConfigStorages(wxMpConfigStorageMap);
        return true;
    }

    /**
     * 刷新
     */
    public boolean refresh(){
        //发送请求
        try {
            wxMpService.getAccessToken();
        } catch (WxErrorException e) {
            log.error("尝试请求token出错!错误信息:",e);
            return false;
        }

        return true;
    }
}
