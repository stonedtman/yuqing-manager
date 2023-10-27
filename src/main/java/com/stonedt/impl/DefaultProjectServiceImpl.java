package com.stonedt.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.dao.DefaultProjectDao;
import com.stonedt.dao.DefaultSolutionGroupDao;
import com.stonedt.entity.DefaultProject;
import com.stonedt.service.DefaultProjectService;
import com.stonedt.vo.DefaultProjectVO;
import com.stonedt.vo.DefaultSolutionGroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 文轩
 */
@Service
public class DefaultProjectServiceImpl implements DefaultProjectService {

    private final DefaultSolutionGroupDao defaultSolutionGroupDao;

    private final DefaultProjectDao defaultProjectDao;

    public DefaultProjectServiceImpl(DefaultSolutionGroupDao defaultSolutionGroupDao,
                                     DefaultProjectDao defaultProjectDao) {
        this.defaultSolutionGroupDao = defaultSolutionGroupDao;
        this.defaultProjectDao = defaultProjectDao;
    }

    /**
     * 获取默认方案组列表
     */
    @Override
    public List<DefaultSolutionGroupVO> getDefaultSolutionGroupList() {
        return defaultSolutionGroupDao.selectAll();
    }

    /**
     * 获取默认方案列表
     */
    @Override
    public List<DefaultProject> getDefaultSolutionList() {
        return defaultProjectDao.selectAll();
    }

    /**
     * 根据方案组id获取方案列表
     *
     * @param groupId
     */
    @Override
    public List<DefaultProjectVO> getDefaultSolutionListByGroupId(Long groupId) {
        List<DefaultProject> defaultProjects = defaultProjectDao.selectByGroupId(groupId);
        return null;
    }

    @Override
    public PageInfo<DefaultProjectVO> getDefaultProjectListPage(Long groupId, Integer page) {

        List<DefaultProject> list = defaultProjectDao.selectByGroupId(groupId);

        return null;
    }
}
