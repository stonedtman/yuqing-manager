package com.stonedt.service;


import com.stonedt.entity.WechatConfig;
import com.stonedt.util.ResultUtil;

/**
 * @author 文轩
 */
public interface WechatConfigServer {

    ResultUtil<Void> updateConfig(WechatConfig wechatConfig, Boolean hasTest);

    ResultUtil<WechatConfig> getConfig();

    ResultUtil<String> getQrCode();

    /**
     * 获取公众号名称
     */
    ResultUtil<String> getAccountName();
}
