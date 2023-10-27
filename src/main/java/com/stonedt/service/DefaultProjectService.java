package com.stonedt.service;


import com.github.pagehelper.PageInfo;
import com.stonedt.entity.DefaultProject;
import com.stonedt.vo.DefaultProjectVO;
import com.stonedt.vo.DefaultSolutionGroupVO;

import java.util.List;

/**
 * 默认方案service
 * @author 文轩
 */
public interface DefaultProjectService {


    /**
     * 获取默认方案组列表
     */
    List<DefaultSolutionGroupVO> getDefaultSolutionGroupList();

    /**
     * 获取默认方案列表
     */
    List<DefaultProject> getDefaultSolutionList();

    /**
     * 根据方案组id获取方案列表
     */
    List<DefaultProjectVO> getDefaultSolutionListByGroupId(Long groupId);


    PageInfo<DefaultProjectVO> getDefaultProjectListPage(Long groupId, Integer page);
}
