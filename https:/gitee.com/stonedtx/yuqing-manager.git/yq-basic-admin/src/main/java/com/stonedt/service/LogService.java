package com.stonedt.service;

import com.alibaba.fastjson.JSONObject;

public interface LogService {
  JSONObject getLoginList(JSONObject paramJSONObject);
  
  JSONObject getUseList(JSONObject paramJSONObject);
}
