package com.stonedt.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface CompanyService {
    JSONObject getCompanyList(Map<String, Object> map);

    JSONObject addCompany(Map data);

    JSONObject updateCompany(Map data);

    JSONObject getCompanyById(String id);
}
