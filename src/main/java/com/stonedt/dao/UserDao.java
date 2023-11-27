package com.stonedt.dao;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.entity.OrganizationEntity;
import com.stonedt.entity.UserEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stonedt.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {
  UserEntity verifyAcountByphone(@Param("telephone") String paramString);
  
  Integer verifyAcount(@Param("telephone") String paramString);
  
  List<Map<String, Object>> getUserList( Map<String, Object> paramMap);
  
  List<Map<String, Object>> getorganizationIdByName(@Param("map") Map<String, Object> paramMap);
  
  Integer addUserInfo(UserEntity paramUserEntity);
  
  Integer addUserorganizeInfo(OrganizationEntity paramOrganizationEntity);
  
  Map<String, Object> getUserInfoByUserId(@Param("user_id") String paramString);

  UserEntity getUser(String oldTelephone);

  OrganizationEntity getOrganizationById(String organization_id);

  int updateUserByTelephone(Map userData);

  int updateterm_of_validity(Map userData);

    List<Map> getSystemlogs(Map map);

    List<Map<String, Object>> getorganizationList();

    int addCompany(Map data);

    Map getProjectByProjectId(Map data);

  int updateUserState(Map data);

    int updatePassword(Map data);

    List<Map<String, Object>> getCompanyList(@Param("map") Map<String, Object> map);

  int updateCompany(Map data);

  JSONObject getCompanyById(String id);

    List<Map<String , String>> getUsers();

  List<Map<String  , Object>> getGroupListByuserId(String userId);

  List<Map<String, String>> getProjectListByGroupId(Map map);

  Map<String, String> getGroupByGroupId(String group_id);

  int addGroup(Map map);

  int updateProject(Map projectByProjectId);

  int addProject(Map<String, Object> projectByProjectId);

  int updateProjectTask(Map<String, Object> project);

  int addProjectTask(Map<String, Object> project);

  Map getProjectTask(Map groupMap);

  int updateGroupDel(String addGroupId);

  int addOpinionCondition(Map<String, Object> project);

  Map getOpinionCondition(String project_id);

  Map getWarningSetting(String project_id);

  int addWarningSetting(Map warningSetting);

  List<Map> getSystemlogsGroup(Map<Object, Object> map);

  List<Map> getUserLoginLog(Map<Object, Object> map);

    int updateOrganizationById(HashMap<Object, Object> organizationMap);

  List<Map> getProjectListByuserId(Map userId);

  List<Map> getProjectList(Map map);

  /**
   * 根据条件获取日志数量
   * @param systemlog 条件
   * @return 数量
   */
  int getCountByCondition(Map systemlog);


  /**
   * 删除时间小于输入时间的日志
   */
    void deleteSystemlogWhenLessTime(Date time);

    List<UseRankVO> getUserUseRanking(@Param("username") String username,
                                      @Param("start") Date start,
                                      @Param("isASC") boolean isASC);

  List<UseRankVO> getAllUserUseRanking(String username);

  List<UserTrendChartVO> getUserTrend(@Param("start") LocalDate start);

  List<SystemHotModuleVO> getSystemHotModuleRanking(@Param("start") Date start,
                                                    @Param("orderType") Integer orderType,
                                                    @Param("size") Integer size);

  List<String> getSystemModuleList();

  List<ModelUseChartVO> getModuleUseChart(@Param("userId") Integer userId,
                                          @Param("module") String module);

  List<UserUseRecord> getUserModuleUseRecord(@Param("userId")Integer userId,
                                             @Param("start")Date start);

  List<String> getSystemSubModuleList(String module);

  List<ModelUseChartVO> getSubModuleUseChart(@Param("module") String module,
                                             @Param("subModule") String subModule);
}
