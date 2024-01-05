package com.stonedt.yuqingwechat.service;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 微信服务接口
 * @author 文轩
 */
public interface WechatService {


    /**
     * 扫码事件处理
     * @param wxMpXmlMessage 微信消息
     * @return 回复消息
     */
    WxMpXmlOutMessage handleScan(WxMpXmlMessage wxMpXmlMessage);

    /**
     * 授权事件处理
     * @param prefix 前缀
     * @param userInfo 用户信息
     * @return 回复消息
     */
    RedirectView redirect(String prefix, WxOAuth2UserInfo userInfo);

}
