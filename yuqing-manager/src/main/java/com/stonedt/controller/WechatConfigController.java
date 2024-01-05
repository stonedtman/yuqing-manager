package com.stonedt.controller;

import com.stonedt.entity.WechatConfig;
import com.stonedt.service.WechatConfigServer;
import com.stonedt.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @author 文轩
 */
@ResponseBody
@RequestMapping("/wechat/config")
public class WechatConfigController {

    private final WechatConfigServer wechatConfigServer;

    public WechatConfigController(WechatConfigServer wechatConfigServer) {
        this.wechatConfigServer = wechatConfigServer;
    }


    /**
     * 修改配置
     */
    @PutMapping
    public ResultUtil<Void> updateConfig(@RequestBody WechatConfig wechatConfig,@RequestParam(required = false) Boolean hasTest){
        return wechatConfigServer.updateConfig(wechatConfig,hasTest);
    }

    /**
     * 获取配置
     */
    @GetMapping
    public ResultUtil<WechatConfig> getConfig() {
        return wechatConfigServer.getConfig();
    }
}
