package com.stonedt.dao;

import com.stonedt.entity.DefaultOpinionCondition;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 文轩
* @description 针对表【default_opinion_condition】的数据库操作Mapper
* @createDate 2023-10-27 19:58:53
* @Entity com.stonedt.intelligence.entity.DefaultOpinionCondition
*/
@Mapper
public interface DefaultOpinionConditionDao {

    /**
     * 查询默认偏好设置
     * @param projectId 项目id
     * @return
     */
    DefaultOpinionCondition getByProjectId(Long projectId);


    void insert(DefaultOpinionCondition condition);

    DefaultOpinionCondition selectByProjectId(Long projectId);

    void update(DefaultOpinionCondition condition);
}




