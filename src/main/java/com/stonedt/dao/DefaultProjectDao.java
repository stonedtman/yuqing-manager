package com.stonedt.dao;

import com.stonedt.entity.DefaultProject;
import com.stonedt.vo.DefaultProjectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 文轩
* @description 针对表【default_project】的数据库操作Mapper
* @createDate 2023-10-27 14:47:17
* @Entity com.stonedt.intelligence.entity.DefaultProject
*/
@Mapper
public interface DefaultProjectDao {

    /**
     * 查询所有数据
     * @return 对象列表
     */
    List<DefaultProject> selectAll();

    /**
     * 根据方案组id获取方案列表
     * @param groupId 方案组id
     * @return 对象列表
     */
    List<DefaultProject> selectByGroupId(Long groupId);

    void updateProjectStatus(@Param("projectId") Long projectId, @Param("status") Integer status);

    void insert(DefaultProject defaultProject);

    boolean existProjectName(@Param("projectName") String projectName, @Param("groupId") Long groupId);
}




