package com.stonedt.yuqingwechat.wechat;

import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 微信重定向分发器
 * @author 文轩
 */
public interface WechatRedirectHandler {

    /**
     * 重定向处理
     *
     * @param prefix   前缀
     * @param userInfo 授权码
     * @return 重定向视图
     */
    RedirectView handle(String prefix, WxOAuth2UserInfo userInfo);


    /**
     * 重载配置
     */
    void reload(WechatClientsProperties wechatClientsProperties);
}
