package com.stonedt.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.Objects;

/**
 * 用户使用记录
 * @author 文轩
 */
public class UserUseRecord {

    private String module;

    private String submodule;

    private String type;

    private Integer count;

    /**
     * 操作系统
     */
    private String operatingSystem;

    private String loginip;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUseTime;


    public UserUseRecord() {
    }

    public UserUseRecord(String module, String submodule, String type, Integer count, String operatingSystem, String loginip, Date lastUseTime) {
        this.module = module;
        this.submodule = submodule;
        this.type = type;
        this.count = count;
        this.operatingSystem = operatingSystem;
        this.loginip = loginip;
        this.lastUseTime = lastUseTime;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSubmodule() {
        return submodule;
    }

    public void setSubmodule(String submodule) {
        this.submodule = submodule;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public Date getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(Date lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserUseRecord that = (UserUseRecord) o;
        return Objects.equals(module, that.module) && Objects.equals(submodule, that.submodule) && Objects.equals(type, that.type) && Objects.equals(count, that.count) && Objects.equals(operatingSystem, that.operatingSystem) && Objects.equals(loginip, that.loginip) && Objects.equals(lastUseTime, that.lastUseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module, submodule, type, count, operatingSystem, loginip, lastUseTime);
    }

    @Override
    public String toString() {
        return "UserUseRecord{" +
                "module='" + module + '\'' +
                ", submodule='" + submodule + '\'' +
                ", type='" + type + '\'' +
                ", count=" + count +
                ", operatingSystem='" + operatingSystem + '\'' +
                ", loginip='" + loginip + '\'' +
                ", lastUseTime=" + lastUseTime +
                '}';
    }
}
