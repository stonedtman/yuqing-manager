package com.stonedt.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.dao.DefaultOpinionConditionDao;
import com.stonedt.dao.DefaultProjectDao;
import com.stonedt.dao.DefaultSolutionGroupDao;
import com.stonedt.entity.DefaultOpinionCondition;
import com.stonedt.entity.DefaultProject;
import com.stonedt.entity.DefaultSolutionGroup;
import com.stonedt.service.DefaultProjectService;
import com.stonedt.util.ProjectUtil;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.DefaultProjectVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 文轩
 */
@Service
public class DefaultProjectServiceImpl implements DefaultProjectService {

    private final DefaultSolutionGroupDao defaultSolutionGroupDao;

    private final DefaultProjectDao defaultProjectDao;

    private final DefaultOpinionConditionDao defaultOpinionConditionDao;

    public DefaultProjectServiceImpl(DefaultSolutionGroupDao defaultSolutionGroupDao,
                                     DefaultProjectDao defaultProjectDao,
                                     DefaultOpinionConditionDao defaultOpinionConditionDao) {
        this.defaultSolutionGroupDao = defaultSolutionGroupDao;
        this.defaultProjectDao = defaultProjectDao;
        this.defaultOpinionConditionDao = defaultOpinionConditionDao;
    }

    /**
     * 获取默认方案组列表
     */
    @Override
    public List<DefaultSolutionGroup> getDefaultSolutionGroupList() {
        return defaultSolutionGroupDao.selectAll();
    }

    /**
     * 获取默认方案列表
     */
    @Override
    public List<DefaultProject> getDefaultSolutionList() {
        return defaultProjectDao.selectAll();
    }

    @Override
    public PageInfo<DefaultSolutionGroup> getDefaultSolutionList(Integer pageNum) {
        PageHelper.startPage(pageNum, 10);
        List<DefaultSolutionGroup> defaultSolutionGroupList = defaultSolutionGroupDao.selectAll();
        return new PageInfo<>(defaultSolutionGroupList);
    }

    /**
     * 根据方案组id获取方案列表
     *
     * @param groupId
     * @param pageNum
     */
    @Override
    public PageInfo<DefaultProject> getDefaultSolutionListByGroupId(Long groupId, Integer pageNum) {
        if (groupId == 0) {
            PageHelper.startPage(pageNum, 10);
            List<DefaultProject> defaultProjectList = defaultProjectDao.selectAll();
            return new PageInfo<>(defaultProjectList);
        }
        PageHelper.startPage(pageNum, 10);
        List<DefaultProject> defaultProjectList = defaultProjectDao.selectByGroupId(groupId);
        return new PageInfo<>(defaultProjectList);
    }

    @Override
    public PageInfo<DefaultProjectVO> getDefaultProjectListPage(Long groupId, Integer page) {

        List<DefaultProject> list = defaultProjectDao.selectByGroupId(groupId);

        return null;
    }

    /**
     * @param projectId
     * @param status
     */
    @Override
    public void updateProjectStatus(Long projectId, Integer status) {
        defaultProjectDao.updateProjectStatus(projectId, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil addProject(JSONObject jsonObject) {
        JSONObject dataObject = jsonObject.getJSONObject("project");
        String projectName = dataObject.getString("project_name");
        String subjectWord = dataObject.getString("subject_word");
        String stopWord = dataObject.getString("stop_word");
        Integer projectType = dataObject.getInteger("project_type");
        Long groupId = dataObject.getLong("group_id");

        // 项目名称去重
        if (defaultProjectDao.existProjectName(projectName, groupId)) {
            return ResultUtil.build(500, "同一方案下项目名称不能重复！！");
        }

        stopWord = ProjectUtil.dealProjectWords(stopWord);
        String regionalWord = "";
        String eventWord = "";
        String characterWord = "";
        subjectWord = ProjectUtil.dealProjectWords(subjectWord);

        subjectWord = ProjectUtil.mergeProjectWords(subjectWord);


        DefaultProject defaultProject = new DefaultProject();
        defaultProject.setProject_name(projectName);
        defaultProject.setSubject_word(subjectWord);
        defaultProject.setStop_word(stopWord);
        defaultProject.setProject_type(projectType);
        defaultProject.setGroup_id(groupId);
        defaultProject.setDel_status(0);

        defaultProject.setRegional_word(regionalWord);
        defaultProject.setEvent_word(eventWord);
        defaultProject.setCharacter_word(characterWord);


        DefaultOpinionCondition condition = jsonObject.getObject("condition", DefaultOpinionCondition.class);
        //默认方案的偏好设置
        condition.setMatchs(1);
        condition.setPrecise(0);
        condition.setEmotion("[1,2,3]");
        condition.setSimilar(0);
        condition.setSort(1);
        condition.setTime(1);
//        String times = condition.getTimes() + " 00:00:00";
//        String timee = condition.getTimee() + " 23:59:59";
//        condition.setTimes(times);
//        condition.setTimee(timee);
        long nextId = IdUtil.createSnowflake(1, 1).nextId();
        condition.setProjectId(nextId);
        defaultProject.setProject_id(nextId);
        long id = IdUtil.createSnowflake(2, 1).nextId();
        condition.setOpinionConditionId(id);
        defaultProjectDao.insert(defaultProject);
        defaultOpinionConditionDao.insert(condition);

        return ResultUtil.ok();

    }

    @Override
    public void updateGroupStatus(Long groupId, Integer status) {
        defaultSolutionGroupDao.updateGroupStatus(groupId, status);
    }

    @Override
    public ResultUtil addDefaultGroup(String groupName) {
        // 方案组名称去重
        if (defaultSolutionGroupDao.existGroupName(groupName)) {
            return ResultUtil.build(500, "方案组名称不能重复！！");
        }
        DefaultSolutionGroup defaultSolutionGroup = new DefaultSolutionGroup();
        defaultSolutionGroup.setGroup_name(groupName);
        defaultSolutionGroup.setDel_status(0);
        long nextId = IdUtil.getSnowflake(3, 1).nextId();
        defaultSolutionGroup.setGroup_id(nextId);
        defaultSolutionGroupDao.insert(defaultSolutionGroup);
        return ResultUtil.ok();
    }

    @Override
    public ResultUtil updateGroup(Long groupId, String groupName) {
        // 方案组名称去重
        if (defaultSolutionGroupDao.existGroupName(groupName)) {
            return ResultUtil.build(500, "方案组名称不能重复！！");
        }
        defaultSolutionGroupDao.updateGroup(groupId, groupName);
        return ResultUtil.ok();
    }

    @Override
    public DefaultSolutionGroup getGroupById(Integer groupId) {
        return defaultSolutionGroupDao.selectById(groupId);
    }

    @Override
    public ResultUtil getProjectDetail(Long projectId) {
        DefaultProject defaultProject = defaultProjectDao.selectByProjectId(projectId);
        DefaultOpinionCondition defaultOpinionCondition = defaultOpinionConditionDao.selectByProjectId(projectId);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(defaultProject));
        jsonObject.put("source_website", defaultOpinionCondition.getWebsitename());
        jsonObject.put("author", defaultOpinionCondition.getAuthor());

        return ResultUtil.ok(jsonObject);
    }

    /**
     * 更新方案
     *
     * @param jsonObject
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil updateProject(JSONObject jsonObject) {
        JSONObject dataObject = jsonObject.getJSONObject("project");
        String projectName = dataObject.getString("project_name");
        String subjectWord = dataObject.getString("subject_word");
        String stopWord = dataObject.getString("stop_word");
        Integer projectType = dataObject.getInteger("project_type");
        Long groupId = dataObject.getLong("group_id");
        Long projectId = dataObject.getLong("project_id");

        // 项目名称去重
        if (defaultProjectDao.existProjectName(projectName, groupId)) {
            return ResultUtil.build(500, "同一方案下项目名称不能重复！！");
        }

        stopWord = ProjectUtil.dealProjectWords(stopWord);
//        String regionalWord = "";
//        String eventWord = "";
//        String characterWord = "";
        subjectWord = ProjectUtil.dealProjectWords(subjectWord);

        subjectWord = ProjectUtil.mergeProjectWords(subjectWord);

        DefaultProject defaultProject = new DefaultProject();
        defaultProject.setProject_name(projectName);
        defaultProject.setSubject_word(subjectWord);
        defaultProject.setStop_word(stopWord);
        defaultProject.setProject_type(projectType);
        defaultProject.setGroup_id(groupId);
        defaultProject.setDel_status(0);
        defaultProject.setProject_id(projectId);

        DefaultOpinionCondition condition = jsonObject.getObject("condition", DefaultOpinionCondition.class);
        condition.setProjectId(projectId);
        defaultProjectDao.update(defaultProject);
        defaultOpinionConditionDao.update(condition);
        return ResultUtil.ok();
    }

}
