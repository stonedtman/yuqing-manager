package com.stonedt.yuqingwechat.properties;

import lombok.Data;

import java.util.List;

/**
 * 微信公众号客户端配置
 * @author 文轩
 */
@Data
public class WechatClientsProperties {

    /**
     * 设置微信公众号的appid
     */
    private String appId;

    /**
     * 设置微信公众号的app secret
     */
    private String secret;

    /**
     * 设置微信公众号的token
     */
    private String token;

    /**
     * 设置微信公众号的EncodingAESKey
     */
    private String aesKey = "sha1";

    /**
     * 模板id
     */
    private String templateId;


    private List<WechatClient> clients;


    @Data
    public static class WechatClient {
        /**
         * 客户端前缀
         */
        private String prefix;

        /**
         * 客户端回调地址
         */
        private String callback;

        /**
         * 客户端重定向地址
         */
        private String redirect;

        /**
         * 授权信息地址
         */
        private String authorize;

        /**
         * 取消关注地址
         */
        private String unsubscribe;

        /**
         * 关注地址
         */
        private String subscribe;
    }

}
