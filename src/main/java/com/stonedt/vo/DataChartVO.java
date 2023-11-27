package com.stonedt.vo;

import java.util.List;
import java.util.Objects;

/**
 * 数据图表VO
 * @author 文轩
 */
public class DataChartVO {

    /**
     * 今日数据总量
     */
    private Integer todayDataCount;

    /**
     * 7日数据总量
     */
    private Integer weekDataCount;

    /**
     * 数据总量
     */
    private Integer dataCount;

    /**
     * 分日数据量
     */
    private List<DataChartDay> dataChartDayVOList;

    public static class DataChartDay {
        /**
         * 日期
         */
        private String date;

        /**
         * 数据量
         */
        private Integer count;

        public DataChartDay() {
        }

        public DataChartDay(String date, Integer count) {
            this.date = date;
            this.count = count;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DataChartDay that = (DataChartDay) o;
            return Objects.equals(date, that.date) && Objects.equals(count, that.count);
        }

        @Override
        public int hashCode() {
            return Objects.hash(date, count);
        }

        @Override
        public String toString() {
            return "DataChartDay{" +
                    "date=" + date +
                    ", count=" + count +
                    '}';
        }
    }


    public DataChartVO() {
    }

    public DataChartVO(Integer todayDataCount, Integer weekDataCount, Integer dataCount, List<DataChartDay> dataChartDayVOList) {
        this.todayDataCount = todayDataCount;
        this.weekDataCount = weekDataCount;
        this.dataCount = dataCount;
        this.dataChartDayVOList = dataChartDayVOList;
    }

    public Integer getTodayDataCount() {
        return todayDataCount;
    }

    public void setTodayDataCount(Integer todayDataCount) {
        this.todayDataCount = todayDataCount;
    }

    public Integer getWeekDataCount() {
        return weekDataCount;
    }

    public void setWeekDataCount(Integer weekDataCount) {
        this.weekDataCount = weekDataCount;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }

    public List<DataChartDay> getDataChartDayVOList() {
        return dataChartDayVOList;
    }

    public void setDataChartDayVOList(List<DataChartDay> dataChartDayVOList) {
        this.dataChartDayVOList = dataChartDayVOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataChartVO that = (DataChartVO) o;
        return Objects.equals(todayDataCount, that.todayDataCount) && Objects.equals(weekDataCount, that.weekDataCount) && Objects.equals(dataCount, that.dataCount) && Objects.equals(dataChartDayVOList, that.dataChartDayVOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(todayDataCount, weekDataCount, dataCount, dataChartDayVOList);
    }

    @Override
    public String toString() {
        return "DataChartVO{" +
                "todayDataCount=" + todayDataCount +
                ", weekDataCount=" + weekDataCount +
                ", dataCount=" + dataCount +
                ", dataChartDayVOList=" + dataChartDayVOList +
                '}';
    }
}
