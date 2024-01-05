package com.stonedt.yuqingwechat.constant;

/**
 * 微信相关常量
 *
 * @author 文轩
 */
public class WechatConstant {


    /**
     * 获取微信公众号二维码的接口地址
     */
    public static final String GET_QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";

    /**
     * 二维码图片url前缀
     */
    public static final String QRCODE_URL_PREFIX = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

    /**
     * 根据openid获取用户信息的接口地址
     */
    public static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info";

    /**
     * 微信公众号用户授权地址
     */
    public static final String AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
}
