package com.stonedt.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.dao.UserDao;
import com.stonedt.entity.OrganizationEntity;
import com.stonedt.entity.UserEntity;
import com.stonedt.service.UserService;
import com.stonedt.util.DateUtil;
import com.stonedt.util.MD5Util;
import com.stonedt.util.SnowFlake;
import com.stonedt.util.SnowflakeUtil;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Transactional
@Service
public class UserServiceImpl implements UserService {
  @Autowired
  UserDao userDao;
  
  public JSONObject verifyAcount(String telephone, String password, HttpSession session) {
    JSONObject response = new JSONObject();
    UserEntity userEntity = this.userDao.verifyAcountByphone(telephone);
    if (userEntity != null) {
      if (userEntity.getTerm_of_validity().after(new Date())) {
        response.put("code", Integer.valueOf(500));
        response.put("msg", "账号已过期！");
      }
      String pwd = userEntity.getPassword();
      if (MD5Util.getMD5(password).equals(pwd)) {
        response.put("code", Integer.valueOf(200));
        response.put("msg", "登录成功");
        session.setAttribute("User", userEntity);
      } else {
        response.put("code", Integer.valueOf(201));
        response.put("msg", "密码错误");
      } 
    } else {
      response.put("code", Integer.valueOf(500));
      response.put("msg", "用户不存在");
    } 
    return response;
  }
  
  public JSONObject getUserList(Map<String, Object> map) {
    JSONObject response = new JSONObject();
    try {
      //搜索内容
      String keyword = String.valueOf(map.get("keyword"));

      String pageStr = String.valueOf(map.get("page"));
      Integer page = Integer.valueOf(pageStr);
      PageHelper.startPage(page.intValue(), 20);
      List<Map<String, Object>> list = this.userDao.getUserList(map);
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
  
  /*public JSONObject addUser(Map<String, Object> map) {
    JSONObject response = new JSONObject();
    try {
      String telephone = String.valueOf(map.get("telephone"));
      Integer userCount = this.userDao.verifyAcount(telephone);
      if (userCount.intValue() == 0) {
        List<Map<String, Object>> organizeList = this.userDao.getorganizationIdByName(map);
        String organization_id = "";
        String organization_code = "";
        String organization_type = "";
        String create_time = DateUtil.getNowTime();
        map.put("create_time", create_time);
        String user_id = String.valueOf(SnowflakeUtil.getId());
        String user_type = String.valueOf(map.get("user_type"));
        String user_level = String.valueOf(map.get("user_level"));
        String username = String.valueOf(map.get("username"));
        String password = String.valueOf(map.get("password"));
        password = MD5Util.getMD5(password);
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(password);
        userEntity.setUser_type(Integer.valueOf(user_type));
        userEntity.setUser_level(Integer.valueOf(user_level));
        userEntity.setUsername(username);
        userEntity.setTelephone(telephone);
        userEntity.setCreate_time(create_time);
        userEntity.setStatus(Integer.valueOf(1));
        userEntity.setEmail("");
        userEntity.setWechat_number("");
        userEntity.setOpenid("");
        userEntity.setUser_id(user_id);
        if (organizeList.size() > 0) {
          for (int i = 0; i < organizeList.size(); i++) {
            if (i == 0) {
              Map<String, Object> organizeMap = organizeList.get(i);
              organization_id = String.valueOf(organizeMap.get("organization_id"));
            } 
          }
          userEntity.setOrganization_id(organization_id);
          map.put("organization_id", organization_id);
          Integer count = addUserInfo(userEntity);
          if (count.intValue() > 0) {
            response.put("code", Integer.valueOf(200));
            response.put("msg", "新增用户成功");
          } else {
            response.put("code", Integer.valueOf(500));
            response.put("msg", "新增用户失败");
          } 
        } else {
          String system_title = String.valueOf(map.get("system_title"));
          String organization_short = String.valueOf(map.get("organization_short"));
          String organization_name = String.valueOf(map.get("organization_name"));
          String term_of_validityStr = String.valueOf(map.get("term_of_validity"));
          JSONObject timeJson = DateUtil.getDifferOneDayTimes(Integer.valueOf(term_of_validityStr));
          String term_of_validity = timeJson.getString("timee");
          organization_id = String.valueOf(SnowflakeUtil.getId());
          OrganizationEntity organizationEntity = new OrganizationEntity();
          organizationEntity.setCreate_time(create_time);
          organizationEntity.setLogo_url("");
          organizationEntity.setTerm_of_validity(term_of_validity);
          organizationEntity.setOrganization_code("6666");
          organizationEntity.setOrganization_id(organization_id);
          organizationEntity.setOrganization_type(Integer.valueOf(1));
          organizationEntity.setStatus(Integer.valueOf(1));
          organizationEntity.setSystem_title(system_title);
          organizationEntity.setOrganization_name(organization_name);
          organizationEntity.setOrganization_short(organization_short);
          Integer organizeCount = this.userDao.addUserorganizeInfo(organizationEntity);
          if (organizeCount.intValue() > 0) {
            userEntity.setOrganization_id(organization_id);
            Integer count = addUserInfo(userEntity);
            if (count.intValue() > 0) {
              response.put("code", Integer.valueOf(200));
              response.put("msg", "新增用户成功");
            } else {
              response.put("code", Integer.valueOf(500));
              response.put("msg", "新增用户失败");
            } 
          } else {
            response.put("code", Integer.valueOf(500));
            response.put("msg", "新增用户失败");
          } 
        } 
      } else {
        response.put("code", Integer.valueOf(500));
        response.put("msg", "用户已存在");
      } 
    } catch (Exception e) {
      e.printStackTrace();
      response.put("code", Integer.valueOf(500));
      response.put("msg", "新增用户失败");
    } 
    return response;
  }*/

    @Override
    public JSONObject addUser(Map<String, Object> map) {
        JSONObject response = new JSONObject();

        try {
          UserEntity user = new UserEntity();
          SnowFlake snowFlake = new SnowFlake();
          Long userId = snowFlake.getId();
          map.put("user_id" , userId);
          String password = String.valueOf(map.get("password"));
          password = MD5Util.getMD5(password);

          String username = (String) map.get("username");
          username = username.trim();
          //用户名只能为2到20位汉字或者2到20位英文字母(含空格)

          if (!username.matches("[a-zA-Z\\s]{2,20}")
                  &&!username.matches("^[\\u4e00-\\u9fa5\\u3400-\\u4db5\\u2e80-\\u2fdf]{2,20}$")){
            response.put("code", Integer.valueOf(500));
            response.put("msg", "用户名只能为2到20位汉字或者2到20位英文字母(含空格)");
            return response;
          }
          String organization_id = String.valueOf(map.get("organization_id"));
          Integer user_level = Integer.valueOf(String.valueOf(map.get("user_level")));
          Integer user_type = Integer.valueOf((String) map.get("user_type"));
          String telephone = String.valueOf(map.get("telephone"));
          if (!telephone.matches("^\\d{11}$")) {
            response.put("code", Integer.valueOf(500));
            response.put("msg", "手机号格式不正确");
            return response;
          }
          UserEntity checkUser = userDao.getUser(telephone);
          if (checkUser != null) {
            response.put("code", Integer.valueOf(500));
            response.put("msg", "该手机号已经注册");
            return response;
          }
          Long term_of_validityStr = Long.valueOf(String.valueOf(map.get("term_of_validity")));


          user.setUser_id(telephone);
          user.setUsername(username);
          user.setPassword(password);
          user.setOrganization_id(organization_id);
          user.setCreate_time(DateUtil.nowTime());
          user.setUser_type(user_type);
          user.setUser_level(user_level);
          user.setTelephone(telephone);
          user.setStatus(Integer.valueOf(1));
          user.setEmail("");
          user.setWechat_number("");
          user.setOpenid("");
          //获取当前时间戳
          long nowTime = System.currentTimeMillis();
          user.setTerm_of_validity(new Date(nowTime+term_of_validityStr*24*60*60*1000));
          Integer count = addUserInfo(user);
          if (count > 0){
            response.put("code", Integer.valueOf(200));
            response.put("msg", "新增用户成功");
          }else {
            response.put("code", Integer.valueOf(500));
            response.put("msg", "新增用户失败");
          }


        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

  @Override
  public Map getUser(String oldTelephone) {

    UserEntity user = null;

    Map<Object, Object> map = new HashMap<>();

    if (oldTelephone != null){
      user = userDao.getUser(oldTelephone);

      List<Map<String , Object>> oList = userDao.getorganizationList();

      for (Map<String, Object> stringObjectMap : oList) {
        Object organization_id = stringObjectMap.get("organization_id");
        stringObjectMap.put("organization_id" , organization_id.toString());
      }


      map.put("user" , user);
      map.put("organization" , oList);
    }

    return map;
  }

  @Override
  public String updateUserByTelephone(JSONObject userData) {

    Map<String, Object> map = new HashMap<>();

    String institutionId = userData.getString("institutionId");

    //将时间换算；
    Long term_of_validity = userData.getLong("term_of_validity");

    String oldTime = userData.getString("oldTime");

    //拿一个当天的时间
    String nowTime1 = DateUtil.getNowTime();
    nowTime1 = nowTime1.substring(0,10);

    boolean b = DateUtil.compareTime(oldTime, nowTime1);

    if (term_of_validity != 0 || b) {
      long days = term_of_validity * (1 * 24 * 60 * 60 * 1000);

      Date date = new Date();
      long nowTime = date.getTime();
      date.setTime(nowTime + days);

      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String time = simpleDateFormat.format(date);
      userData.put("term_of_validity", time);
    }else{
      userData.put("term_of_validity", oldTime);
    }


    Map<String, Object> params = new HashMap<>();
    params.put("telephone" , userData.getString("telephone"));
    params.put("user_type" , userData.getInteger("user_type"));
    params.put("user_level" , userData.getInteger("user_level"));
    params.put("username" , userData.getString("username"));
    params.put("term_of_validity" , userData.getString("term_of_validity"));
    params.put("organization_id" , userData.getLong("organization_id"));



    try {
      int i = userDao.updateUserByTelephone(params);

      int u = userDao.updateterm_of_validity(params);

    }catch (Exception e){

      e.printStackTrace();
      return "修改失败";
    }


      return "修改成功";


  }



/*  @Override
  public JSONObject transferProject(Map data) {

    JSONObject jo = userDao.getProjectByProjectId(data);
    return null;
  }*/

  @Override
  public JSONObject updateUserState(Map data) {

    JSONObject jo = new JSONObject();
    try {
      int i = userDao.updateUserState(data);

      if (i > 0){
        jo.put("code" , 1);
      }else{
        jo.put("code" , 1);
      }
    }catch (Exception e){
      e.printStackTrace();
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      jo.put("code" , 0);
    }


    return jo;
  }

  @Override
  public JSONObject updatePassword(Map data) {

    JSONObject jo = new JSONObject();
    try {
      String password = (String) data.get("password");

      String s = MD5Util.getMD5(password);
      data.put("password" , s);
      int i = userDao.updatePassword(data);

      if (i > 0) {
        jo.put("code", 200);
      }else {
        jo.put("code", 0);
      }
    }catch (Exception e){
      System.out.println("改密码报错");
      e.printStackTrace();
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      jo.put("code", 500);
    }


    return jo;
  }

    @Override
    public List getOrganizationList() {
        List<Map<String, Object>> mapList = userDao.getorganizationList();
        return mapList;
    }

  @Override
  public JSONObject getCompanyList(JSONObject jo) {

    JSONObject object = new JSONObject();
    try {
      Map<String, Object> map = new HashMap<>();
      List<Map<String, Object>> companyList = userDao.getCompanyList(map);
      object.put("code" , 200);
      object.put("data" , companyList);
    }catch (Exception e){
      e.printStackTrace();
      object.put("code" , 500);
    }

    return object;
  }

  public Integer addUserInfo(UserEntity userEntity) {
    Integer count = Integer.valueOf(0);
    try {
      count = this.userDao.addUserInfo(userEntity);
    } catch (Exception e) {
      e.printStackTrace();
    } 
    return count;
  }



}
