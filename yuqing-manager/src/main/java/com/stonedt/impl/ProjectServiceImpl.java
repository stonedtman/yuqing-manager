package com.stonedt.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.dao.UserDao;
import com.stonedt.service.ProjectService;
import com.stonedt.util.DateUtil;
import com.stonedt.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private UserDao userDao;

    @Override
    public JSONObject getUsers() {
        JSONObject users = new JSONObject();
        try {

            List<Map<String , String>> data = userDao.getUsers();
            if (data != null){
                users.put("data" , data);
                users.put("code" , 200);
            }else {
                users.put("code" , 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            users.put("code" , 500);
        }
        return users;
    }

    @Override
    public JSONObject getGroupListByuserId(String userId) {
        JSONObject groupList = new JSONObject();

        try {
            List<Map<String  , Object>> data = userDao.getGroupListByuserId(userId);
            if (data != null){
                groupList.put("code" , 200);
                groupList.put("data" , data);
            }else{
                groupList.put("code" , 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            groupList.put("code" , 500);
        }

        
        return groupList;
    }

    @Override
    public JSONObject getProjectListByGroupId(Map map) {
        JSONObject object = new JSONObject();

        try {
            Integer page = (Integer) map.get("page");
            PageHelper pageHelper = new PageHelper();
            pageHelper.startPage(page , 20);
            List<Map<String ,String>> data = userDao.getProjectListByGroupId(map);
            PageInfo<Map<String, String>> mapPageInfo = new PageInfo<>(data);
            if (data != null){
                object.put("code" , 200);
                object.put("data" , mapPageInfo);
            }else{
                object.put("code" , 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            object.put("code" , 500);
        }

        return object;
    }

    @Override
    public JSONObject copy(Map data) {

        JSONObject object = new JSONObject();

        try {


        //创建时间
        String nowTime = DateUtil.getNowTime();
        //存入的方案组名
        String addGroupId = "";

        String user_id = (String) data.get("user_id");
        String project_id = (String) data.get("project_id");


        Map groupMap = new HashMap<>();
        groupMap.put("project_id" , project_id);
        Map projectByProjectId = userDao.getProjectByProjectId(groupMap);
        String group_id = String.valueOf(projectByProjectId.get("group_id"));



        //方案组区
        List<Map<String, Object>> groupList = userDao.getGroupListByuserId(user_id);

        Map<String , String> Group = userDao.getGroupByGroupId(group_id);
        String group_name = Group.get("group_name");

        //判断是否有重名方案组
        boolean b = true;
        for (Map<String, Object> group : groupList) {
            if (group_name.equals(group.get("group_name"))){
                b = false;
                addGroupId = ((Long)group.get("group_id")).toString();
                int i = userDao.updateGroupDel(addGroupId);
            }
        }

        //没有则创建方案组
        if (b){
            addGroupId = Long.valueOf(new SnowFlake().getId()).toString();
            Map map = new HashMap<>();
            map.put("create_time" , nowTime);
            map.put("group_id" , addGroupId);
            map.put("group_name" , group_name);
            map.put("user_id" , user_id);

            int i = userDao.addGroup(map);
            if (i < 1){
                object.put("code" , 0);
                return object;
            }
        }


        //方案区

        groupMap.put("group_id" ,addGroupId );
        //获取存入方案组所有的方案
        List<Map<String, String>> ProjectLis = userDao.getProjectListByGroupId(groupMap);
        groupMap.put("project_id" , project_id);
        //获取需要拷贝的方案
        Map<String , Object> project = userDao.getProjectByProjectId(groupMap);

        //获取被拷贝的projectTask
        Map projectTask = userDao.getProjectTask(groupMap);
        if (projectTask == null){
            projectTask = new HashMap();
        }
        //被拷贝方案名称
        String project_name = (String)project.get("project_name");

        boolean pb = true;
        //如果获取的方案集合不为空，判断各方案名与拷贝方案名是否一致
            if (ProjectLis != null) {
                for (Map<String, String> oneProject : ProjectLis) {
                    if (project_name.equals(oneProject.get("project_name"))) {
                        project.put("update_time", nowTime);
                        project.put("project_id", oneProject.get("project_id"));
                        project.put("del_status", '0');

                        projectTask.put("project_id", oneProject.get("project_id"));

                        //如果有，进行覆盖
                        int i = userDao.updateProject(project);
                        int j = userDao.updateProjectTask(project);
                        pb = false;
                    }
                }
            }


        if (pb){
            //没有对应方案进行创建
            //新的方案id
            String pId = Long.valueOf(new SnowFlake().getId()).toString();
            project.put("create_time" , nowTime);
            project.put("project_id" , pId);
            project.put("update_time" , nowTime);
            project.put("group_id" , addGroupId);
            project.put("user_id" , user_id);

            projectTask.put("create_time" , nowTime);
            projectTask.put("project_id" , pId);

            int i = userDao.addProject(project);

            if (i < 1){
                object.put("code" , 0);
                return object;
            }
            int j = userDao.addProjectTask(projectTask);

            //设置
            //获取被拷贝设置
            Map opinionCondition = userDao.getOpinionCondition(project_id);
            if (opinionCondition == null){
                opinionCondition = new HashMap();
            }
            opinionCondition.put("create_time" , nowTime);
            opinionCondition.put("opinion_condition_id" , Long.valueOf(new SnowFlake().getId()).toString());
            opinionCondition.put("project_id" , pId);
            int o = userDao.addOpinionCondition(opinionCondition);

            //预警
            //获取被拷贝预警
            Map warningSetting = userDao.getWarningSetting(project_id);
            if (warningSetting == null){
                warningSetting = new HashMap();
            }
            warningSetting.put("create_time" , nowTime);
            warningSetting.put("project_id" , pId);
            warningSetting.put("warning_setting_id" , Long.valueOf(new SnowFlake().getId()).toString());
            warningSetting.put("warning_status" , 0);
            warningSetting.put("weekend_warning" , 0);
            int w = userDao.addWarningSetting(warningSetting);



        }

            object.put("code" , 200);
        }catch (Exception e){
            e.printStackTrace();
            //手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            object.put("code" , 500);
        }

        return object;
    }

    @Override
    public JSONObject getProjectListByuserId(Map map) {
        JSONObject object = new JSONObject();
        try {
            Integer page = (Integer) map.get("page");
            PageHelper pageHelper = new PageHelper();
            pageHelper.startPage(page , 20);
            List<Map> data = userDao.getProjectListByuserId(map);
            PageInfo<Object> mapPageInfo = new PageInfo(data);
            if (data != null){
                object.put("code" , 200);
                object.put("data" , mapPageInfo);
            }else{
                object.put("code" , 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            object.put("code" , 500);
        }

        return object;
    }

    @Override
    public JSONObject getProjectList(Map map) {

        JSONObject object = new JSONObject();
        try {
            Integer page = (Integer) map.get("page");
            PageHelper pageHelper = new PageHelper();
            pageHelper.startPage(page , 20);
            List<Map> data = userDao.getProjectList(map);
            PageInfo<Object> mapPageInfo = new PageInfo(data);
            if (data != null){
                object.put("code" , 200);
                object.put("data" , mapPageInfo);
            }else{
                object.put("code" , 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            object.put("code" , 500);
        }

        return object;

    }
}
