package com.stonedt.vo;

import java.util.Objects;

/**
 * 数据记录
 * @author 文轩
 */
public class DataRecord {

    private String id;

    /**
     * 数据来源
     */
    private String source;

    /**
     * 数据量
     */
    private Integer count;

    /**
     * 最新入库时间
     */
    private String lastSpiderTime;

    /**
     * 最新发布时间
     */
    private String lastPublishTime;


    public DataRecord() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLastSpiderTime() {
        return lastSpiderTime;
    }

    public void setLastSpiderTime(String lastSpiderTime) {
        this.lastSpiderTime = lastSpiderTime;
    }

    public String getLastPublishTime() {
        return lastPublishTime;
    }

    public void setLastPublishTime(String lastPublishTime) {
        this.lastPublishTime = lastPublishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataRecord that = (DataRecord) o;
        return Objects.equals(id, that.id) && Objects.equals(source, that.source) && Objects.equals(count, that.count) && Objects.equals(lastSpiderTime, that.lastSpiderTime) && Objects.equals(lastPublishTime, that.lastPublishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, count, lastSpiderTime, lastPublishTime);
    }

    @Override
    public String toString() {
        return "DataRecord{" +
                "id='" + id + '\'' +
                ", source='" + source + '\'' +
                ", count=" + count +
                ", lastSpiderTime='" + lastSpiderTime + '\'' +
                ", lastPublishTime='" + lastPublishTime + '\'' +
                '}';
    }
}
