package com.stonedt.yuqingwechat;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 文轩
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(YuqingWechatAutoConfig.class)
public @interface EnableWechat {


}
