package com.stonedt.yuqingwechat.wechat;

import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 函数式接口
 * 用于处理微信回调
 * @author 文轩
 */
public interface WechatCallbackHandler {

    /**
     * 处理微信回调
     *
     * @param eventData 微信回调事件数据
     * @return
     */
    WxMpXmlOutMessage handle(WxMpXmlMessage eventData);


    /**
     * 重载配置
     */
    void reload(WechatClientsProperties wechatClientsProperties);


}
