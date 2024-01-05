package com.stonedt.yuqingwechat.handler;

import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import com.stonedt.yuqingwechat.thread.ThreadPoolConst;
import com.stonedt.yuqingwechat.wechat.WechatRedirectHandler;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信重定向默认处理器
 * @author 文轩
 */
@Slf4j
public class DefaultRedirectHandler implements WechatRedirectHandler {

    private final RestTemplate restTemplate;

    private Map<String, WechatClientsProperties.WechatClient> clients;

    public DefaultRedirectHandler(RestTemplate restTemplate,
                                  Map<String, WechatClientsProperties.WechatClient> clients) {
        this.restTemplate = restTemplate;
        this.clients = clients;
    }

    /**
     * 重定向处理
     *
     * @param prefix   前缀
     * @param userInfo 授权码
     * @return 重定向视图
     */
    @Override
    public RedirectView handle(String prefix, WxOAuth2UserInfo userInfo) {
        // 获取微信客户端配置
        WechatClientsProperties.WechatClient client = clients.get(prefix);
        if (client == null) {
            log.error("微信客户端配置不存在");
            return null;
        }
        //获取授权回调地址
        String authorizeUrl = client.getAuthorize();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<WxOAuth2UserInfo> request = new HttpEntity<>(userInfo, headers);
        // 传输数据
        ThreadPoolConst.IO_EXECUTOR.execute(() -> restTemplate.postForObject(authorizeUrl, request, String.class));
        // 获取重定向地址
        String redirectUrl = client.getRedirect();
        // 重定向
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(redirectUrl);
        return redirectView;
    }

    /**
     * 重载配置
     *
     * @param wechatClientsProperties
     */
    @Override
    public void reload(WechatClientsProperties wechatClientsProperties) {
        assert wechatClientsProperties != null;
        Map<String, WechatClientsProperties.WechatClient> clients = new HashMap<>();
        List<WechatClientsProperties.WechatClient> wechatClients = wechatClientsProperties.getClients();
        for (WechatClientsProperties.WechatClient wechatClient : wechatClients) {
            assert wechatClient.getRedirect() != null;
            clients.put(wechatClient.getPrefix(), wechatClient);
        }
        this.clients = clients;
    }
}
