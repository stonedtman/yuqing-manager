package com.stonedt.yuqingwechat;

import com.stonedt.yuqingwechat.config.WechatConfigOperation;
import com.stonedt.yuqingwechat.controller.WechatController;
import com.stonedt.yuqingwechat.handler.DefaultCallbackHandler;
import com.stonedt.yuqingwechat.handler.DefaultRedirectHandler;
import com.stonedt.yuqingwechat.handler.message.LogHandler;
import com.stonedt.yuqingwechat.handler.message.ScanHandler;
import com.stonedt.yuqingwechat.handler.message.SubscribeHandler;
import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import com.stonedt.yuqingwechat.service.WechatService;
import com.stonedt.yuqingwechat.service.impl.WechatServiceImpl;
import com.stonedt.yuqingwechat.wechat.WechatCallbackHandler;
import com.stonedt.yuqingwechat.wechat.WechatDistributor;
import com.stonedt.yuqingwechat.wechat.WechatRedirectHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.api.impl.WxMpTemplateMsgServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;

/**
 * @author 文轩
 */
@AutoConfiguration
@ConditionalOnBean(WechatClientsProperties.class)
public class YuqingWechatAutoConfig {


    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    @ConditionalOnMissingBean
    public WechatRedirectHandler wechatRedirectHandler(RestTemplate restTemplate,
                                                       WechatClientsProperties wechatClientsProperties) {
        Map<String, WechatClientsProperties.WechatClient> clients = new HashMap<>();
        List<WechatClientsProperties.WechatClient> wechatClients = wechatClientsProperties.getClients();
        for (WechatClientsProperties.WechatClient wechatClient : wechatClients) {
            clients.put(wechatClient.getPrefix(), wechatClient);
        }
        return new DefaultRedirectHandler(restTemplate, clients);
    }

    @Bean
    @ConditionalOnMissingBean
    public WechatCallbackHandler wechatCallbackHandler(RestTemplate restTemplate,
                                                       WechatClientsProperties wechatClientsProperties) {
        Map<String, WechatClientsProperties.WechatClient> clients = new HashMap<>();
        List<WechatClientsProperties.WechatClient> wechatClients = wechatClientsProperties.getClients();
        for (WechatClientsProperties.WechatClient wechatClient : wechatClients) {
            clients.put(wechatClient.getPrefix(), wechatClient);
        }

        return new DefaultCallbackHandler(restTemplate, clients, wechatClientsProperties.getAppId());
    }

    @Bean
    @ConditionalOnMissingBean
    public WechatDistributor wechatDistributor(WechatRedirectHandler wechatRedirectHandler,
                                               WechatCallbackHandler wechatCallbackHandler) {
        return WechatDistributor.builder()
                .registerDefaultCallbackHandler(wechatCallbackHandler)
                .registerDefaultRedirectHandler(wechatRedirectHandler)
                .build();
    }




    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService(WechatClientsProperties wechatClientsProperties) {
        if (wechatClientsProperties == null) {
            throw new RuntimeException("配置有误！");
        }
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(wechatClientsProperties.getAppId());
        configStorage.setSecret(wechatClientsProperties.getSecret());
        configStorage.setToken(wechatClientsProperties.getToken());
        configStorage.setAesKey(wechatClientsProperties.getAesKey());
        WxMpService service = new WxMpServiceImpl();

        Map<String, WxMpConfigStorage> wxMpConfigStorageMap = new HashMap<>();
        wxMpConfigStorageMap.put(wechatClientsProperties.getAppId(), configStorage);
        service.setMultiConfigStorages(wxMpConfigStorageMap);
        return service;
    }

    @Bean
    @ConditionalOnMissingBean
    public LogHandler logHandler(RestTemplate restTemplate,
                                 WechatClientsProperties wechatClientsProperties) {
        return new LogHandler(wechatClientsProperties,restTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public WechatService wechatService(WechatDistributor wechatDistributor) {
        return new WechatServiceImpl(wechatDistributor);
    }

    @Bean
    @ConditionalOnMissingBean
    public ScanHandler scanHandler(WechatService wechatService) {
        return new ScanHandler(wechatService);
    }

    @Bean
    @ConditionalOnMissingBean
    public SubscribeHandler subscribeHandler(WechatService wechatService) {
        return new SubscribeHandler(wechatService);
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService,
                                           LogHandler logHandler,
                                           ScanHandler scanHandler,
                                           SubscribeHandler subscribeHandler) {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(logHandler).next();

        // 关注事件
        newRouter.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(subscribeHandler).end();

        // 扫码事件
        newRouter.rule().async(false).msgType(EVENT).event(WxConsts.EventType.SCAN).handler(scanHandler).end();

        return newRouter;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpTemplateMsgService wxMpTemplateMsgService(WxMpService wxMpService) {
        return new WxMpTemplateMsgServiceImpl(wxMpService);
    }

    @Bean
    @ConditionalOnMissingBean
    public WechatController wechatController(WechatService wechatService,
                                             WxMpService wxService,
                                             WxMpMessageRouter messageRouter,
                                             WxMpTemplateMsgService wxMpTemplateMsgService,
                                             WechatClientsProperties wechatClientsProperties) {
        return new WechatController(wechatService,wxService,messageRouter,wxMpTemplateMsgService, wechatClientsProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public WechatConfigOperation wechatConfigOperation(WxMpService wxService,
                                                       WechatRedirectHandler wechatRedirectHandler,
                                                       WechatCallbackHandler wechatCallbackHandler,
                                                       WechatClientsProperties wechatClientsProperties) {
        return new WechatConfigOperation(wechatRedirectHandler,wechatCallbackHandler,wxService, wechatClientsProperties);
    }
}
