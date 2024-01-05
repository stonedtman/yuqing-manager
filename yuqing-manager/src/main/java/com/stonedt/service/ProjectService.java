package com.stonedt.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface ProjectService {
    JSONObject getUsers();

    JSONObject getGroupListByuserId(String userId);

    JSONObject getProjectListByGroupId(Map map);

    JSONObject copy(Map data);

    JSONObject getProjectListByuserId(Map map);

    JSONObject getProjectList(Map map);
}
