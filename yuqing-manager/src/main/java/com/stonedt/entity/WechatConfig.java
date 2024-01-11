package com.stonedt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * 
 * @TableName wechat_config
 */

public class WechatConfig implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * appid
     */
    private String appid;

    /**
     * secret
     */
    private String secret;

    /**
     * token
     */
    private String token;

    /**
     * 地址
     */
    private String url;

    /**
     * 回调地址
     */
    private String callback;

    /**
     * 微信公众号名称
     */
    private String name;

    /**
     * 模板id
     */
    private String template_id;


    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private static final long serialVersionUID = 1L;


    public WechatConfig() {
    }

    public WechatConfig(Integer id, String appid, String secret, String token, String url, String callback, String name, String template_id, Date createTime, Date updateTime) {
        this.id = id;
        this.appid = appid;
        this.secret = secret;
        this.token = token;
        this.url = url;
        this.callback = callback;
        this.name = name;
        this.template_id = template_id;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WechatConfig that = (WechatConfig) o;
        return Objects.equals(id, that.id) && Objects.equals(appid, that.appid) && Objects.equals(secret, that.secret) && Objects.equals(token, that.token) && Objects.equals(url, that.url) && Objects.equals(callback, that.callback) && Objects.equals(name, that.name) && Objects.equals(template_id, that.template_id) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, appid, secret, token, url, callback, name, template_id, createTime, updateTime);
    }
}