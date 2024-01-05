package com.stonedt.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.Objects;

/**
 * @author 文轩
 */
public class SystemHotModuleVO {

    private String module;

    private String submodule;

    private String type;

    private Integer total;

    private String mostUsedUsername;

    private Integer count;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endUseTime;

    private Integer userId;

    public SystemHotModuleVO() {
    }

    public SystemHotModuleVO(String module, String submodule, String type, Integer total, String mostUsedUsername, Integer count, Date endUseTime, Integer userId) {
        this.module = module;
        this.submodule = submodule;
        this.type = type;
        this.total = total;
        this.mostUsedUsername = mostUsedUsername;
        this.count = count;
        this.endUseTime = endUseTime;
        this.userId = userId;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getMostUsedUsername() {
        return mostUsedUsername;
    }

    public void setMostUsedUsername(String mostUsedUsername) {
        this.mostUsedUsername = mostUsedUsername;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getEndUseTime() {
        return endUseTime;
    }

    public void setEndUseTime(Date endUseTime) {
        this.endUseTime = endUseTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemHotModuleVO that = (SystemHotModuleVO) o;
        return Objects.equals(module, that.module) && Objects.equals(submodule, that.submodule) && Objects.equals(type, that.type) && Objects.equals(total, that.total) && Objects.equals(mostUsedUsername, that.mostUsedUsername) && Objects.equals(count, that.count) && Objects.equals(endUseTime, that.endUseTime) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module, submodule, type, total, mostUsedUsername, count, endUseTime, userId);
    }


    @Override
    public String toString() {
        return "SystemHotModuleVO{" +
                "module='" + module + '\'' +
                ", submodule='" + submodule + '\'' +
                ", type='" + type + '\'' +
                ", total=" + total +
                ", mostUsedUsername='" + mostUsedUsername + '\'' +
                ", count=" + count +
                ", endUseTime=" + endUseTime +
                ", userId=" + userId +
                '}';
    }
}
