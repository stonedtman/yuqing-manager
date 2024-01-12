//package com.stonedt.config;
//
//import com.stonedt.controller.WechatConfigController;
//import com.stonedt.dao.WechatConfigDao;
//import com.stonedt.entity.WechatConfig;
//import com.stonedt.impl.WechatConfigServerImpl;
//import com.stonedt.service.WechatConfigServer;
//import com.stonedt.yuqingwechat.config.WechatConfigOperation;
//import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author 文轩
// */
//@Configuration
//public class YuqingWechatConfig {
//
//    private final WechatConfigDao wechatConfigDao;
//
//    public YuqingWechatConfig(WechatConfigDao wechatConfigDao) {
//        this.wechatConfigDao = wechatConfigDao;
//    }
//
//
//    @Bean
//    @ConditionalOnMissingBean
//    public WechatClientsProperties wechatClientsProperties() {
//        WechatConfig wechatConfig = wechatConfigDao.selectLast();
//        if (wechatConfig == null || wechatConfig.getAppid() == null || wechatConfig.getSecret() == null ||
//        wechatConfig.getToken() == null || wechatConfig.getTemplate_id() == null || wechatConfig.getUrl() == null ||
//        wechatConfig.getCallback() == null) {
//            WechatClientsProperties wechatClientsProperties = new WechatClientsProperties();
//            List<WechatClientsProperties.WechatClient> wechatClientList = new ArrayList<>();
//            wechatClientsProperties.setClients(wechatClientList);
//            return wechatClientsProperties;
//        }
//        //构造配置对象
//        WechatClientsProperties wechatClientsProperties = new WechatClientsProperties();
//        wechatClientsProperties.setAppId(wechatConfig.getAppid());
//        wechatClientsProperties.setSecret(wechatConfig.getSecret());
//        wechatClientsProperties.setToken(wechatConfig.getToken());
//        wechatClientsProperties.setTemplateId(wechatConfig.getTemplate_id());
//
//        WechatClientsProperties.WechatClient wechatClient = new WechatClientsProperties.WechatClient();
//        wechatClient.setPrefix("yuqing");
//
//        String url = wechatConfig.getUrl();
//        //去除末尾的/,如果有的话
//        if (url.endsWith("/")) {
//            url = url.substring(0, url.length() - 1);
//        }
//        String callback = wechatConfig.getCallback();
//        if (callback.endsWith("/")) {
//            callback = callback.substring(0, callback.length() - 1);
//        }
//        wechatClient.setCallback(callback + "/wechat");
//        wechatClient.setSubscribe(url + "/wechat/handleSubscribe");
//        wechatClient.setRedirect(url + "/mobile/monitor");
//        wechatClient.setUnsubscribe(url + "/wechat/handleUnsubscribe");
//        wechatClient.setAuthorize(url + "/wechat/handleAuthorize");
//
//        List<WechatClientsProperties.WechatClient> wechatClientList = new ArrayList<>();
//        wechatClientList.add(wechatClient);
//
//        wechatClientsProperties.setClients(wechatClientList);
//        return wechatClientsProperties;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public WechatConfigServer wechatConfigServer(WechatConfigOperation wechatConfigOperation,
//                                                 WechatConfigDao wechatConfigDao,
//                                                 RestTemplate restTemplate) {
//        return new WechatConfigServerImpl(wechatConfigOperation,wechatConfigDao, restTemplate);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public WechatConfigController wechatConfigController(WechatConfigServer wechatConfigServer) {
//        return new WechatConfigController(wechatConfigServer);
//    }
//
//}
