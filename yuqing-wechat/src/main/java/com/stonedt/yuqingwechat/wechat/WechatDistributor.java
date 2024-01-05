package com.stonedt.yuqingwechat.wechat;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;

/**
 * 微信回调分发器
 * @author 文轩
 */
public class WechatDistributor {

    /**
     * 回调处理器集合
     */
    private final HashMap<String, WechatCallbackHandler> callbackHandlers = new HashMap<>();

    /**
     * 重定向处理器集合
     */
    private final HashMap<String, WechatRedirectHandler> redirectHandlers = new HashMap<>();

    /**
     * 默认回调处理器
     */
    private WechatCallbackHandler defaultCallbackHandler;

    /**
     * 默认重定向处理器
     */
    private WechatRedirectHandler defaultRedirectHandler;

//    /**
//     * 微信客户端列表
//     */
//    private final HashMap<String, WechatClientsProperties.WechatClient> clients = new HashMap<>();

    /**
     * 私有构造函数
     */
    private WechatDistributor() {
    }


    /**
     * 注册回调处理器
     * @param eventKeyPrefix 事件key前缀
     * @param handler 回调处理器
     */
    public void registerCallbackHandler(String eventKeyPrefix, WechatCallbackHandler handler) {
        callbackHandlers.put(eventKeyPrefix, handler);
    }

    /**
     * 注册默认回调处理器
     */
    public void registerDefaultCallbackHandler(WechatCallbackHandler handler) {
        defaultCallbackHandler = handler;
    }

    /**
     * 注册重定向处理器
     * @param prefix 前缀
     * @param handler 重定向处理器
     */
    public void registerRedirectHandler(String prefix, WechatRedirectHandler handler) {
        redirectHandlers.put(prefix, handler);
    }

    /**
     * 注册默认重定向处理器
     */
    public void registerDefaultRedirectHandler(WechatRedirectHandler handler) {
        defaultRedirectHandler = handler;
    }


    /**
     * 分发给相应的处理器处理微信回调
     * @param eventKeyPrefix 事件key前缀
     * @param eventData 回调事件数据
     * @return 处理结果
     */
    public WxMpXmlOutMessage distributor(String eventKeyPrefix, WxMpXmlMessage eventData) {
        WechatCallbackHandler handler = callbackHandlers.get(eventKeyPrefix);
        if (handler != null) {
            return handler.handle(eventData);
        }
        if (defaultCallbackHandler != null) {
            return defaultCallbackHandler.handle(eventData);
        }
        throw new RuntimeException("未找到相应的回调处理器");
    }

    /**
     * 分发给相应的处理器处理微信回调
     * @param prefix 前缀
     * @param userInfo 用户信息
     */
    public RedirectView distributor(String prefix, WxOAuth2UserInfo userInfo) {
        WechatRedirectHandler handler = redirectHandlers.get(prefix);
        if (handler != null) {
            return handler.handle(prefix, userInfo);
        }
        if (defaultRedirectHandler != null) {
            return defaultRedirectHandler.handle(prefix, userInfo);
        }
        throw new RuntimeException("未找到相应的回调处理器");
    }


    /**
     * 获取建造类
     */
    public static Builder builder() {
        return new Builder();
    }



    /**
     * 建造类
     */
    public static class Builder {

        /**
         * 微信回调分发器
         */
        private final WechatDistributor distributor = new WechatDistributor();


        /**
         * 注册回调处理器
         *
         * @param eventKeyPrefix 事件key前缀
         * @param handler   回调处理器
         * @return 建造类
         */
        public Builder registerCallbackHandler(String eventKeyPrefix, WechatCallbackHandler handler) {
            distributor.registerCallbackHandler(eventKeyPrefix, handler);
            return this;
        }

        /**
         * 注册默认回调处理器
         *
         * @param handler   回调处理器
         * @return 建造类
         */
        public Builder registerDefaultCallbackHandler(WechatCallbackHandler handler) {
            distributor.registerDefaultCallbackHandler(handler);
            return this;
        }

        /**
         * 注册重定向处理器
         *
         * @param prefix 前缀
         * @param handler   重定向处理器
         * @return 建造类
         */
        public Builder registerRedirectHandler(String prefix, WechatRedirectHandler handler) {
            distributor.registerRedirectHandler(prefix, handler);
            return this;
        }

        /**
         * 注册默认重定向处理器
         *
         * @param handler   重定向处理器
         * @return 建造类
         */
        public Builder registerDefaultRedirectHandler(WechatRedirectHandler handler) {
            distributor.registerDefaultRedirectHandler(handler);
            return this;
        }

        /**
         * 构建微信回调分发器
         *
         * @return 微信回调分发器
         */
        public WechatDistributor build() {
            return distributor;
        }
    }


}
