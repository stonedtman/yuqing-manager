package com.stonedt.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.Objects;

/**
 * 模块使用记录
 * @author 文轩
 */
public class ModuleUseRecord {

    private String submodule;

    private String type;

    private Integer total;

    private Integer count;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date lastUseTime;

    private String lastUsedUser;

    private String mostUsedUser;

    public ModuleUseRecord() {
    }

    public ModuleUseRecord(String submodule, String type, Integer total, Integer count, Date lastUseTime, String lastUsedUser, String mostUsedUser) {
        this.submodule = submodule;
        this.type = type;
        this.total = total;
        this.count = count;
        this.lastUseTime = lastUseTime;
        this.lastUsedUser = lastUsedUser;
        this.mostUsedUser = mostUsedUser;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getLastUseTime() {
        return lastUseTime;
    }

    public void setLastUseTime(Date lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    public String getLastUsedUser() {
        return lastUsedUser;
    }

    public void setLastUsedUser(String lastUsedUser) {
        this.lastUsedUser = lastUsedUser;
    }

    public String getMostUsedUser() {
        return mostUsedUser;
    }

    public void setMostUsedUser(String mostUsedUser) {
        this.mostUsedUser = mostUsedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleUseRecord that = (ModuleUseRecord) o;
        return Objects.equals(submodule, that.submodule) && Objects.equals(type, that.type) && Objects.equals(total, that.total) && Objects.equals(count, that.count) && Objects.equals(lastUseTime, that.lastUseTime) && Objects.equals(lastUsedUser, that.lastUsedUser) && Objects.equals(mostUsedUser, that.mostUsedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(submodule, type, total, count, lastUseTime, lastUsedUser, mostUsedUser);
    }

    @Override
    public String toString() {
        return "ModuleUseRecord{" +
                "submodule='" + submodule + '\'' +
                ", type='" + type + '\'' +
                ", total=" + total +
                ", count=" + count +
                ", lastUseTime=" + lastUseTime +
                ", lastUsedUser='" + lastUsedUser + '\'' +
                ", mostUsedUser='" + mostUsedUser + '\'' +
                '}';
    }
}
