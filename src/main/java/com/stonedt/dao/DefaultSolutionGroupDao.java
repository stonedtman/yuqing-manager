package com.stonedt.dao;

import com.stonedt.entity.DefaultSolutionGroup;
import com.stonedt.vo.DefaultSolutionGroupVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 文轩
* @description 针对表【default_solution_group】的数据库操作Mapper
* @createDate 2023-10-27 14:47:17
* @Entity com.stonedt.intelligence.entity.DefaultSolutionGroup
*/
@Mapper
public interface DefaultSolutionGroupDao {

    /**
     * 查询所有数据
     * @return 对象列表
     */
    List<DefaultSolutionGroup> selectAll();

    void updateGroupStatus(@Param("groupId") Long groupId, @Param("status") Integer status);

    boolean existGroupName(String groupName);

    void insert(DefaultSolutionGroup defaultSolutionGroup);

    void updateGroup(@Param("groupId") Long groupId, @Param("groupName") String groupName);

    DefaultSolutionGroup selectByGroupId(Long groupId);

    DefaultSolutionGroup selectById(Integer id);
}




