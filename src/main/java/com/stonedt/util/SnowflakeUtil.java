package com.stonedt.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class SnowflakeUtil {
  public static Long getId() {
    Snowflake snowflake = IdUtil.createSnowflake(1L, 1L);
    long id = snowflake.nextId();
    return Long.valueOf(id);
  }
}