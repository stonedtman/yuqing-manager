package com.stonedt.vo;

import java.util.Date;
import java.util.Objects;

/**
 * @description 用户使用排名VO
 * @author 文轩
 */

public class UseRankVO {

    private Integer id;

    private String username;

    private Integer count;


    private Integer nlpFlag;

    private Integer xieFlag;

    private Integer wechatFlag;

    private Date endLoginTime;


    public UseRankVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getNlpFlag() {
        return nlpFlag;
    }

    public void setNlpFlag(Integer nlpFlag) {
        this.nlpFlag = nlpFlag;
    }

    public Integer getXieFlag() {
        return xieFlag;
    }

    public void setXieFlag(Integer xieFlag) {
        this.xieFlag = xieFlag;
    }

    public Integer getWechatFlag() {
        return wechatFlag;
    }

    public void setWechatFlag(Integer wechatFlag) {
        this.wechatFlag = wechatFlag;
    }

    public Date getEndLoginTime() {
        return endLoginTime;
    }

    public void setEndLoginTime(Date endLoginTime) {
        this.endLoginTime = endLoginTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UseRankVO useRankVO = (UseRankVO) o;
        return Objects.equals(id, useRankVO.id) && Objects.equals(username, useRankVO.username) && Objects.equals(count, useRankVO.count) && Objects.equals(nlpFlag, useRankVO.nlpFlag) && Objects.equals(xieFlag, useRankVO.xieFlag) && Objects.equals(wechatFlag, useRankVO.wechatFlag) && Objects.equals(endLoginTime, useRankVO.endLoginTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, count, nlpFlag, xieFlag, wechatFlag, endLoginTime);
    }

    @Override
    public String toString() {
        return "UseRankVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", count=" + count +
                ", nlpFlag=" + nlpFlag +
                ", xieFlag=" + xieFlag +
                ", wechatFlag=" + wechatFlag +
                ", endLoginTime=" + endLoginTime +
                '}';
    }
}
