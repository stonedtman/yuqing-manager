package com.stonedt.yuqingwechat.handler.message;

import com.alibaba.fastjson2.JSON;
import com.stonedt.yuqingwechat.properties.WechatClientsProperties;
import com.stonedt.yuqingwechat.thread.ThreadPoolConst;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class LogHandler extends AbstractHandler {

    private final WechatClientsProperties wechatClientsProperties;

    private final RestTemplate restTemplate;

    public LogHandler(WechatClientsProperties wechatClientsProperties,
                      RestTemplate restTemplate) {
        this.wechatClientsProperties = wechatClientsProperties;
        this.restTemplate = restTemplate;
    }


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        //如果事件是取消订阅 则删除账户
        String event = wxMessage.getEvent();
        //获取用户id
        String openId = wxMessage.getFromUser();
        List<WechatClientsProperties.WechatClient> clients = wechatClientsProperties.getClients();
        if ("unsubscribe".equals(event)) {
            log.info("用户取消关注，openId：{}", openId);
            for (WechatClientsProperties.WechatClient client : clients) {
                log.info("向{}客户端发送用户取消关注事件,用户openid:{}", client.getPrefix(), openId);
                ThreadPoolConst.IO_EXECUTOR.execute(()-> {
                            Map<String, String> params = new HashMap<>(1);
                            params.put("openId", openId);
                            //传输数据
                            String url = client.getUnsubscribe();
                            try {
                                restTemplate.getForObject(url + "?openId={openId}", String.class, params);
                            } catch (RestClientException e) {
                                log.error("向{}客户端发送用户取消关注事件失败,用户openid:{}", client.getPrefix(), openId);
                                e.printStackTrace();
                            }
                        }
                );

            }

        }
        log.info("\n接收到请求消息，内容：{}", JSON.toJSONString(wxMessage));
        return null;
    }

}
