package com.stonedt.yuqingwechat.handler.message;

import com.stonedt.yuqingwechat.service.WechatService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 文轩
 */
public class ScanHandler extends AbstractHandler {


    private final WechatService wechatService;

    public ScanHandler(WechatService wechatService) {
        this.wechatService = wechatService;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        // 扫码事件处理
        logger.info("正在进行扫码事件处理");
        return wechatService.handleScan(wxMpXmlMessage);
    }

}
