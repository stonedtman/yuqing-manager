package com.stonedt.vo;

import java.util.Objects;

/**
 * 模块使用情况图表VO
 *
 * @author 文轩
 */
public class ModelUseChartVO {


    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 模块使用次数
     */
    private Integer count;


    public ModelUseChartVO() {
    }

    public ModelUseChartVO(String moduleName, Integer count) {
        this.moduleName = moduleName;
        this.count = count;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelUseChartVO that = (ModelUseChartVO) o;
        return Objects.equals(moduleName, that.moduleName) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleName, count);
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ModelUseChartVO{" +
                "moduleName='" + moduleName + '\'' +
                ", count=" + count +
                '}';
    }
}
