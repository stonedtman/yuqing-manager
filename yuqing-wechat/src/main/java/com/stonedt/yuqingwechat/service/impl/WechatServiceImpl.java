package com.stonedt.yuqingwechat.service.impl;

import com.stonedt.yuqingwechat.service.WechatService;
import com.stonedt.yuqingwechat.wechat.WechatDistributor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author 文轩
 * 微信服务接口实现类
 */
@Slf4j
public class WechatServiceImpl implements WechatService {


    private final WechatDistributor wechatDistributor;




    public WechatServiceImpl(WechatDistributor wechatDistributor) {
        this.wechatDistributor = wechatDistributor;
    }



    /**
     * 扫码事件处理
     *
     * @param wxMpXmlMessage 微信消息
     * @return 回复消息
     */
    @Override
    public WxMpXmlOutMessage handleScan(WxMpXmlMessage wxMpXmlMessage) {
        String eventKey = wxMpXmlMessage.getEventKey();
        if (eventKey == null) {
            log.error("扫码事件key为空");
            return null;
        }
        //去除qrscene_前缀
        eventKey = eventKey.replace("qrscene_", "");
        String prefix = eventKey.substring(0, eventKey.indexOf(":"));
        return wechatDistributor.distributor(prefix, wxMpXmlMessage);
    }

    /**
     * 授权事件处理
     *
     * @param prefix   前缀
     * @param userInfo 用户信息
     * @return 回复消息
     */
    @Override
    public RedirectView redirect(String prefix, WxOAuth2UserInfo userInfo) {
        return wechatDistributor.distributor(prefix, userInfo);
    }

}
