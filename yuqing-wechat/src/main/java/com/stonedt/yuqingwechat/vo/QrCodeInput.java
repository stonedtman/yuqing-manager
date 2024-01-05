package com.stonedt.yuqingwechat.vo;

import lombok.Data;

/**
 * 二维码输入参数
 * @author 文轩
 */
@Data
public class QrCodeInput {

    private String sceneStr;

    private Integer expireSeconds;
}
