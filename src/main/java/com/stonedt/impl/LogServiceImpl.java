package com.stonedt.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.dao.UserDao;
import com.stonedt.entity.OrganizationEntity;
import com.stonedt.service.LogService;
import com.stonedt.util.MapUtil;
import com.stonedt.util.MyHttpRequestUtil;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.stonedt.util.ResultUtil;
import com.stonedt.vo.ModelUseChartVO;
import com.stonedt.vo.SystemHotModuleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {
  @Value("${es.search.url}")
  private String es_search_url;
  
  @Autowired
  UserDao userDao;
  
  public JSONObject getLoginList(JSONObject paramJson) {
    JSONObject response = new JSONObject();
    try {
    //搜索关键词
      String keyword = paramJson.getString("keyword");
      String times = paramJson.getString("times");
      Integer page = paramJson.getInteger("page");

      Map<Object, Object> map = new HashMap<>();
      map.put("times" , times);
      PageHelper.startPage(page , 10);

      /*if ( !"".equals(keyword) && keyword != null){
        keyword = "%" + keyword +"%";
      }*/

      map.put("keyword" , keyword);

      List<Map> systemlogs = userDao.getUserLoginLog(map);

      PageInfo pageInfo = new PageInfo(systemlogs);

      List dataArray = pageInfo.getList();
      Long total = pageInfo.getTotal();
      String totalCount = total.toString();
      Integer pages = pageInfo.getPages();
      String totalPage = pages.toString();


      response.put("code", Integer.valueOf(200));
      response.put("data", dataArray);
      response.put("msg", "获取日志信息成功");
      response.put("totalCount", totalCount);
      response.put("totalPage", totalPage);
      response.put("page", page);
    } catch (Exception e) {
      e.printStackTrace();
      response.put("code", Integer.valueOf(500));
      response.put("data", new JSONArray());
      response.put("msg", "获取日志信息失败");
      response.put("totalCount", Integer.valueOf(0));
      response.put("totalPage", Integer.valueOf(1));
      response.put("page", Integer.valueOf(1));
    } 
    return response;
  }


  public JSONObject getUseList(JSONObject paramJson) {
    JSONObject response = new JSONObject();
    Long page = paramJson.getLong("page");
    //搜索内容
    String keyword = paramJson.getString("keyword");

    Map map = new HashMap();
    String times = paramJson.getString("times");
    map.put("times" , times);//获取时间范围

    /*if ( !"".equals(keyword) && keyword != null){
      keyword = "%" + keyword +"%";
    }*/

    map.put("keyword" , keyword);
    PageHelper.startPage(page.intValue() , 10);
    List<Map> systemlogs = userDao.getSystemlogs(map);
    for (Map systemlog : systemlogs) {
      systemlog.put("times", times);
      int count = userDao.getCountByCondition(systemlog);
      systemlog.put("count" , count);
    }

    PageInfo<Map> mapPageInfo = new PageInfo<>(systemlogs);



    response.put("dataList" , mapPageInfo);
    return response;
  }

  @Override
  public ResultUtil<List<SystemHotModuleVO>> getSystemHotModuleRanking(Integer days, Integer orderType, Integer size) {
    Date start = null;
    if (days != null) {
      long currentTimeMillis = System.currentTimeMillis();
      start = new Date(currentTimeMillis - TimeUnit.DAYS.toMillis(days));
    }

    List<SystemHotModuleVO> systemHotModuleVOList = userDao.getSystemHotModuleRanking(start, orderType, size);
    return ResultUtil.ok(systemHotModuleVOList);
  }

  @Override
  public ResultUtil<List<String>> getSystemModuleList() {
    List<String> systemModuleList = userDao.getSystemModuleList();
    return ResultUtil.ok(systemModuleList);
  }

  @Override
  public ResultUtil<List<ModelUseChartVO>> getModuleUseChart(String module) {
    List<ModelUseChartVO> modelUseChartVOList = userDao.getModuleUseChart(module);
    return ResultUtil.ok(modelUseChartVOList);
  }


  
  /*public JSONObject getUseList(JSONObject paramJson) {
    JSONObject response = new JSONObject();
    try {
      JSONArray dataArray = new JSONArray();
      String url = this.es_search_url + "/userlog/loginnum";
      String params = MapUtil.getUrlParamsByMap((Map)paramJson);
      String esResponse = MyHttpRequestUtil.sendPostEsSearch(url, params);
      JSONObject esResponseJson = JSON.parseObject(esResponse);
      JSONObject aggregationsJson = esResponseJson.getJSONObject("aggregations");
      JSONObject group_by_tagsJson = aggregationsJson.getJSONObject("group_by_tags");
      JSONArray bucketsArray = group_by_tagsJson.getJSONArray("buckets");
      Integer totalCount = Integer.valueOf(bucketsArray.size());
      Integer totalPage = Integer.valueOf(0);
      if (totalCount.intValue() % 10 == 0) {
        totalPage = Integer.valueOf(totalCount.intValue() / 10);
      } else {
        totalPage = Integer.valueOf(totalCount.intValue() / 10 + 1);
      } 
      Integer page = paramJson.getInteger("page");
      Integer startIndex = Integer.valueOf((page.intValue() - 1) * 10);
      Integer stopIndex = Integer.valueOf(page.intValue() * 10 - 1);
      for (int i = 0; i < bucketsArray.size(); i++) {
        if (i >= startIndex.intValue()) {
          JSONObject bucketJson = bucketsArray.getJSONObject(i);
          String key = bucketJson.getString("key");
          Integer loginCount = bucketJson.getInteger("doc_count");
          Map<String, Object> userInfoMap = this.userDao.getUserInfoByUserId(key);
          userInfoMap.put("loginCount", loginCount);
          dataArray.add(userInfoMap);
          if (i == stopIndex.intValue())
            break; 
        } 
      } 
      response.put("code", Integer.valueOf(200));
      response.put("data", dataArray);
      response.put("msg", "获取日志信息成功");
      response.put("totalCount", totalCount);
      response.put("totalPage", totalPage);
      response.put("page", page);
    } catch (Exception e) {
      e.printStackTrace();
      response.put("code", Integer.valueOf(500));
      response.put("data", new JSONArray());
      response.put("msg", "获取日志信息失败");
      response.put("totalCount", Integer.valueOf(0));
      response.put("totalPage", Integer.valueOf(1));
      response.put("page", Integer.valueOf(1));
    } 
    return response;
  }*/
}
