package com.stonedt.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * @description 用户趋势图VO
 * @author 文轩
 */
public class UserTrendChartVO implements Serializable {

    @JSONField(format = "yyyy-MM-dd")
    private LocalDate date;

    private Integer count;

    @Override
    public String toString() {
        return "UserTrendChartVO{" +
                "date=" + date +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTrendChartVO that = (UserTrendChartVO) o;
        return Objects.equals(date, that.date) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, count);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
