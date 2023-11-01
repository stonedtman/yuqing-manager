package com.stonedt.service;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.stonedt.entity.DefaultProject;
import com.stonedt.entity.DefaultSolutionGroup;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.DefaultProjectVO;

import java.util.List;

/**
 * 默认方案service
 * @author 文轩
 */
public interface DefaultProjectService {


    /**
     * 获取默认方案组列表
     */
    List<DefaultSolutionGroup> getDefaultSolutionGroupList();

    /**
     * 获取默认方案列表
     */
    List<DefaultProject> getDefaultSolutionList();

    PageInfo<DefaultSolutionGroup> getDefaultSolutionList(Integer pageNum);

    /**
     * 根据方案组id获取方案列表
     */
    PageInfo<DefaultProject> getDefaultSolutionListByGroupId(Long groupId, Integer pageNum);


    PageInfo<DefaultProjectVO> getDefaultProjectListPage(Long groupId, Integer page);

    /**
     *
     * @param projectId
     * @param status
     */
    void updateProjectStatus(Long projectId, Integer status);

    ResultUtil addProject(JSONObject jsonObject);

    void updateGroupStatus(Long groupId, Integer status);

    ResultUtil addDefaultGroup(String groupName);

    ResultUtil updateGroup(Long groupId, String groupName);


    DefaultSolutionGroup getGroupById(Integer groupId);
}
