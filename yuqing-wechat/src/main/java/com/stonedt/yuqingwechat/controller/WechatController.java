package com.stonedt.yuqingwechat.controller;

import com.stonedt.yuqingwechat.constant.WechatConstant;
import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import com.stonedt.yuqingwechat.service.WechatService;
import com.stonedt.yuqingwechat.vo.QrCodeInput;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.*;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 微信控制器
 * @author 文轩
 */
@Slf4j
@ResponseBody
@RequestMapping("/wechat")
public class WechatController {

    private final WechatService wechatService;

    private final WxMpService wxService;

    private final WxMpMessageRouter messageRouter;

    private final WxMpTemplateMsgService wxMpTemplateMsgService;

    private final WechatClientsProperties wechatClientsProperties;

    public WechatController(WechatService wechatService,
                            WxMpService wxService,
                            WxMpMessageRouter messageRouter,
                            WxMpTemplateMsgService wxMpTemplateMsgService,
                            WechatClientsProperties wechatClientsProperties) {
        this.wechatService = wechatService;
        this.wxService = wxService;
        this.messageRouter = messageRouter;
        this.wxMpTemplateMsgService = wxMpTemplateMsgService;
        this.wechatClientsProperties = wechatClientsProperties;
    }

    /**
     * 获取微信二维码地址
     */
//    @Operation(summary = "获取微信二维码地址",
//            description = "获取微信二维码地址",
//    parameters = {
//            @Parameter(name = "sceneStr", description = "场景值", required = true),
//            @Parameter(name = "expireSeconds", description = "二维码有效时间", required = true)
//    },
//            responses = {
//            @ApiResponse(responseCode = "200", description = "成功"),
//            @ApiResponse(responseCode = "400", description = "失败")
//            }
//    )
    @PostMapping("/getQrCode")
    public String getQrCode(@RequestBody QrCodeInput qrCodeInput) {
        String sceneStr = qrCodeInput.getSceneStr();
        Integer expireSeconds = qrCodeInput.getExpireSeconds();
        log.info("获取微信二维码地址,sceneStr:{},expireSeconds:{}", sceneStr, expireSeconds);
        if (sceneStr == null || expireSeconds == null) {
            log.error("参数错误");
            return null;
        }
        if (expireSeconds > 2592000) {
            log.error("二维码有效时间不能超过2592000秒");
            return null;
        }
        // 如果场景值不以前缀开头,则提示错误
        if(!sceneStr.contains(":")){
            log.error("场景值错误");
            return null;
        }

        try {
            WxMpQrCodeTicket wxMpQrCodeTicket = wxService.getQrcodeService().qrCodeCreateTmpTicket(sceneStr, expireSeconds);
            String ticket = wxMpQrCodeTicket.getTicket();
            log.info("获取微信二维码地址成功,sceneStr:{},expireSeconds:{},ticket:{}", sceneStr, expireSeconds, ticket);
            return WechatConstant.QRCODE_URL_PREFIX + ticket;
        } catch (WxErrorException e) {
            log.error("获取微信二维码地址失败", e);
            return null;
        }
    }

    /**
     * 微信回调,服务器验证
     */
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String callback(String signature, String timestamp, String nonce, String echostr) {
        log.info("微信服务器验证");
        if (wxService.checkSignature(timestamp, nonce, signature)) {
            log.info("微信服务器验证成功");
            return echostr;
        }
        log.info("微信服务器验证失败");
        return null;
    }

    /**
     * 微信回调,处理微信回调事件
     */
    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(@RequestBody String requestBody,
                       @RequestParam("signature") String signature,
                       @RequestParam("timestamp") String timestamp,
                       @RequestParam("nonce") String nonce,
                       @RequestParam("openid") String openid,
                       @RequestParam(name = "encrypt_type", required = false) String encType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        log.info("\n接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }

        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
                    timestamp, nonce, msgSignature);
            log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
        }

        log.debug("\n组装回复信息：{}", out);
        return out;
    }


    @GetMapping("/redirect/{prefix}")
    public RedirectView redirect(@RequestParam String code, @PathVariable String prefix) {

        WxOAuth2UserInfo userInfo = null;
        try {
            WxOAuth2AccessToken accessToken = wxService.getOAuth2Service().getAccessToken(code);
            userInfo = wxService.getOAuth2Service().getUserInfo(accessToken, "zh_CN");

        } catch (WxErrorException e) {
            log.error("获取用户信息失败", e);
        }
        return wechatService.redirect(prefix, userInfo);


    }


    /**
     * 向用户推送消息
     */
    @PostMapping("/send")
    public String send(@RequestBody WxMpTemplateMessage wxMpTemplateMessage) throws WxErrorException {;
        String templateId = wxMpTemplateMessage.getTemplateId();
        if (templateId == null) {
            wxMpTemplateMessage.setTemplateId(wechatClientsProperties.getTemplateId());
        }
        return wxMpTemplateMsgService.sendTemplateMsg(wxMpTemplateMessage);
    }




    /**
     *
     * @param message
     * @return
     */

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }

        return null;
    }

}
