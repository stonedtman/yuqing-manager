package com.stonedt.vo;

import java.util.Objects;

/**
 * @author 文轩
 */

public class SubscribeInfoVO {

    /**
     * 关注者数量
     */
    private Integer subscribe;

    /**
     * 预警数量
     */
    private Integer warning;

    public SubscribeInfoVO() {
    }

    public SubscribeInfoVO(Integer subscribe, Integer warning) {
        this.subscribe = subscribe;
        this.warning = warning;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public Integer getWarning() {
        return warning;
    }

    public void setWarning(Integer warning) {
        this.warning = warning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscribeInfoVO that = (SubscribeInfoVO) o;
        return Objects.equals(subscribe, that.subscribe) && Objects.equals(warning, that.warning);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscribe, warning);
    }

    @Override
    public String toString() {
        return "SubscribeInfoVO{" +
                "subscribe=" + subscribe +
                ", warning=" + warning +
                '}';
    }
}
