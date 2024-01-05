package com.stonedt.service;


import com.stonedt.entity.WechatConfig;
import com.stonedt.util.ResultUtil;

/**
 * @author 文轩
 */
public interface WechatConfigServer {

    ResultUtil<Void> updateConfig(WechatConfig wechatConfig, Boolean hasTest);

    ResultUtil<WechatConfig> getConfig();
}
