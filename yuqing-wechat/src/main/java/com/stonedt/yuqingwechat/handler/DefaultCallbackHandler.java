package com.stonedt.yuqingwechat.handler;

import com.alibaba.fastjson2.JSON;
import com.stonedt.yuqingwechat.constant.WechatConstant;
import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import com.stonedt.yuqingwechat.uitl.TextBuilder;
import com.stonedt.yuqingwechat.wechat.WechatCallbackHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认回调处理器
 * @author 文轩
 */
@Slf4j
@Data
public class DefaultCallbackHandler implements WechatCallbackHandler {

    private final RestTemplate restTemplate;
    private Map<String, WechatClientsProperties.WechatClient> clients;

    private String appId;

    public DefaultCallbackHandler(RestTemplate restTemplate,
                                  Map<String, WechatClientsProperties.WechatClient> clients,
                                  String appId) {
        this.restTemplate = restTemplate;
        this.clients = clients;
        this.appId = appId;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage eventData) {
        //获取事件key
        String eventKey = eventData.getEventKey();
        //去除qrscene_前缀
        eventKey = eventKey.replace("qrscene_", "");
        //获取前缀
        String prefix = eventKey.substring(0, eventKey.lastIndexOf(":"));
        //获取微信客户端配置
        WechatClientsProperties.WechatClient client = clients.get(prefix);
        // 获取回调地址
        String callbackUrl = client.getCallback();
        // 获取关注事件地址
        String subscribeUrl = client.getSubscribe();

        // 传输数据
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json; charset=UTF-8");
            HttpEntity<String> request = new HttpEntity<>(JSON.toJSONString(eventData), headers);
            Boolean isExist = restTemplate.postForObject(subscribeUrl, request, Boolean.class);
            if (isExist == null) {
                return new TextBuilder().build("登录失败,请联系管理员", eventData);
            }
            if (Boolean.FALSE.equals(isExist)) {
                String callback = callbackUrl + "/redirect/" + prefix;
                log.info("用户不存在，跳转授权，地址：{}", callback);
                String skipUrl = String.format(WechatConstant.AUTH_URL, appId, URLEncoder.encode(callback, "UTF-8"));
                WxMpXmlOutMessage.TEXT().build();
                // 获取当前时间
                LocalDateTime now = LocalDateTime.now();
                // 定义日期时间格式化模式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                // 格式化当前时间为字符串
                String formattedDateTime = now.format(formatter);
                return new TextBuilder().build("请点击授权：<a href=\"" + skipUrl + "\">点击此处完成注册/绑定</a> \n当前时间：" + formattedDateTime, eventData);
            }else {
                return new TextBuilder().build("登录成功！", eventData);
            }
        } catch (RestClientException | UnsupportedEncodingException e) {
            log.error("回调失败", e);
        }
        return null;
    }

    /**
     * 重载配置
     *
     * @param wechatClientsProperties
     */
    @Override
    public void reload(WechatClientsProperties wechatClientsProperties) {
        assert wechatClientsProperties != null;
        String newAppId = wechatClientsProperties.getAppId();
        assert newAppId != null;

        Map<String, WechatClientsProperties.WechatClient> clients = new HashMap<>();
        List<WechatClientsProperties.WechatClient> wechatClients = wechatClientsProperties.getClients();
        for (WechatClientsProperties.WechatClient wechatClient : wechatClients) {
            assert wechatClient.getCallback() != null;
            clients.put(wechatClient.getPrefix(), wechatClient);
        }
        this.clients = clients;
        this.appId = newAppId;
    }
}
