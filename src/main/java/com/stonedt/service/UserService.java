package com.stonedt.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.ModelUseChartVO;
import com.stonedt.vo.UseRankVO;
import com.stonedt.vo.UserTrendChartVO;
import com.stonedt.vo.UserUseRecord;

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

  ResultUtil<PageInfo<UseRankVO>> getUserUseRanking(String username, Integer days, Integer pageNum, Integer pageSize, boolean isASC);

  ResultUtil<List<UserTrendChartVO>> getUserTrend(Integer days);

  ResultUtil<PageInfo<UserUseRecord>> getUserModuleUseRecord(Integer userId, Integer days, Integer pageNum, Integer pageSize);

  List<String> getSystemSubModuleList(String module);

  List<ModelUseChartVO> getSubModuleUseChart(String module, String subModule);
}
