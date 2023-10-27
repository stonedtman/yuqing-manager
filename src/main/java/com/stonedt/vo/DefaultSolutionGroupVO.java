package com.stonedt.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author 文轩
 * @TableName default_solution_group
 */
public class DefaultSolutionGroupVO implements Serializable {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 方案组公共id
     */
    private String group_id;

    /**
     * 方案组名称
     */
    private String group_name;

    /**
     * 软删除（0：否 1：是）
     */
    private Integer del_status;

    private static final long serialVersionUID = 1L;

    public DefaultSolutionGroupVO() {
    }

    public DefaultSolutionGroupVO(Integer id, Date create_time, String group_id, String group_name, Integer del_status) {
        this.id = id;
        this.create_time = create_time;
        this.group_id = group_id;
        this.group_name = group_name;
        this.del_status = del_status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultSolutionGroupVO that = (DefaultSolutionGroupVO) o;
        return Objects.equals(id, that.id) && Objects.equals(create_time, that.create_time) && Objects.equals(group_id, that.group_id) && Objects.equals(group_name, that.group_name) && Objects.equals(del_status, that.del_status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, create_time, group_id, group_name, del_status);
    }

    @Override
    public String toString() {
        return "DefaultSolutionGroupVO{" +
                "id=" + id +
                ", create_time=" + create_time +
                ", group_id='" + group_id + '\'' +
                ", group_name='" + group_name + '\'' +
                ", del_status=" + del_status +
                '}';
    }
}