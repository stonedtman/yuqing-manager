package com.stonedt.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.dao.UserDao;
import com.stonedt.service.CompanyService;
import com.stonedt.util.DateUtil;
import com.stonedt.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private UserDao userDao;

    @Override
    public JSONObject getCompanyList(Map<String, Object> map) {

        JSONObject response = new JSONObject();
        try {
            String pageStr = String.valueOf(map.get("page"));
            Integer page = Integer.valueOf(pageStr);
            PageHelper.startPage(page.intValue(), 20);
            List<Map<String, Object>> list = this.userDao.getCompanyList(map);
            PageInfo<Map<String, Object>> pageInfo = new PageInfo(list);
            Integer totalPage = Integer.valueOf(pageInfo.getPages());
            Long totalCount = Long.valueOf(pageInfo.getTotal());
            response.put("code", Integer.valueOf(200));
            response.put("data", list);
            response.put("page", page);
            response.put("totalPage", totalPage);
            response.put("totalCount", totalCount);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", Integer.valueOf(500));
            response.put("data", new ArrayList());
            response.put("page", Integer.valueOf(1));
            response.put("totalPage", Integer.valueOf(1));
            response.put("totalCount", Integer.valueOf(0));
        }

        return response;
    }


    @Override
    public JSONObject addCompany(Map data) {
        JSONObject jo = new JSONObject();
        try {
            String nowTime = DateUtil.getNowTime();
            data.put("create_time" , nowTime);
            Long term_of_validity = Long.valueOf((String)data.get("term_of_validity"));
            long days = term_of_validity * (1 * 24 * 60 * 60 * 1000);

            Date date = new Date();
            long now = date.getTime();
            date.setTime(now + days);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(date);
            data.put("term_of_validity", time);

            //创建企业公共id
            SnowFlake snowFlake = new SnowFlake();
            data.put("organization_id" , snowFlake.getId());

            int i = userDao.addCompany(data);
            if (i > 0){
                jo.put("code" , 200);
            }else {
                jo.put("code" , 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            jo.put("code" , 500);
        }


        return jo;
    }

    @Override
    public JSONObject updateCompany(Map data) {
        JSONObject jo = new JSONObject();
        try {
            String nowTime = DateUtil.getNowTime();
            data.put("create_time" , nowTime);
            Long term_of_validity = Long.valueOf((String)data.get("term_of_validity"));
            long days = term_of_validity * (1 * 24 * 60 * 60 * 1000);

            Date date = new Date();
            long now = date.getTime();
            date.setTime(now + days);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(date);
            data.put("term_of_validity", time);

            int i = userDao.updateCompany(data);
            if (i > 0){
                jo.put("code" , 200);
            }else {
                jo.put("code" , 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            jo.put("code" , 500);
        }


        return jo;
    }

    @Override
    public JSONObject getCompanyById(String id) {


        JSONObject map = userDao.getCompanyById(id);

        return map;
    }
}
