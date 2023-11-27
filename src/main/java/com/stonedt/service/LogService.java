package com.stonedt.service;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.SystemHotModuleVO;

import java.util.List;

public interface LogService {
  JSONObject getLoginList(JSONObject paramJSONObject);
  
  JSONObject getUseList(JSONObject paramJSONObject);

  ResultUtil<List<SystemHotModuleVO>> getSystemHotModuleRanking(Integer days, Integer orderType, Integer size);
}
