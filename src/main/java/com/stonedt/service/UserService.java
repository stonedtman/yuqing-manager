package com.stonedt.service;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.entity.UserEntity;
import com.stonedt.util.ResultUtil;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

public interface UserService {
  JSONObject verifyAcount(String paramString1, String paramString2, HttpSession paramHttpSession);
  
  JSONObject getUserList(Map<String, Object> paramMap);
  
  JSONObject addUser(Map<String, Object> paramMap);

  Map getUser(String oldTelephone);

    String updateUserByTelephone(JSONObject userData);


    /*JSONObject transferProject(Map data);*/

  JSONObject updateUserState(Map data);

  JSONObject updatePassword(Map data);

    List getOrganizationList();

    JSONObject getCompanyList(JSONObject jo);

  ResultUtil getUserUseRanking(String username, Integer days, Integer pageNum, Integer pageSize);
}
