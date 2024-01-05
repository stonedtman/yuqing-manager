package com.stonedt.yuqingwechat.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 回调事件数据
 * @author 文轩
 */
@Data
public class CallbackEventData {

    /**
     * 事件的目标微信公众号
     */
    private String toUserName;

    /**
     * 事件来自哪个用户(openId)
     */
    private String fromUserName;

    /**
     * 事件创建时间
     */
    private Date createTime;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 事件数据
     */
    private String event;

    /**
     * 事件key(场景值)
     */
    private String eventKey;

    /**
     * 二维码的ticket
     */
    private String ticket;
}
