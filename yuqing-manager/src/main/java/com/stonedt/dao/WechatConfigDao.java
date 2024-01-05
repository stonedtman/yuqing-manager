package com.stonedt.dao;

import com.stonedt.entity.WechatConfig;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 文轩
* @description 针对表【wechat_config】的数据库操作Mapper
* @createDate 2024-01-03 15:31:49
* @Entity com.stonedt.entity.WechatConfig
*/
@Mapper
public interface WechatConfigDao {

    void update(WechatConfig wechatConfig);

    WechatConfig selectLast();
}




