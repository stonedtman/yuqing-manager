package com.stonedt.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 综合看板表
 * @TableName synthesize
 */
public class Synthesize implements Serializable {
    /**
     * 自增长id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Long user_id;

    /**
     * 创建时间
     */
    private Date cteate_time;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 日报
     */
    private String report_day;

    /**
     * 周报
     */
    private String report_week;

    /**
     * 微博热点
     */
    private String hot_weibo;

    /**
     * 热点事件
     */
    private String hot_all;

    /**
     * 热点搜索词
     */
    private String hot_search_terms;

    /**
     * 领导人舆情
     */
    private String leaders_PO;

    /**
     * 今日舆情情况
     */
    private String today_PO_status;

    /**
     * 预警舆情展示
     */
    private String warning_PO;

    /**
     * 个人信息报送
     */
    private String upload_PO;

    /**
     * 专题展示
     */
    private String project_po_status;

    /**
     * 系统当前在线统计
     */
    private String online;

    /**
     *  推送舆情
     */
    private String push_PO;

    /**
     * 转载查询
     */
    private String reprint_PO;

    /**
     * 收藏贴文
     */
    private String collection_po;

    /**
     * 微信热点
     */
    private String hot_wechat;

    /**
     * 抖音
     */
    private String hot_douyin;

    /**
     * bilibili
     */
    private String hot_bilibili;

    /**
     * 腾讯视频
     */
    private String hot_tecentvedio;

    /**
     * 
     */
    private String hot_36kr;

    /**
     * 
     */
    private String hot_finaceData;

    /**
     * 
     */
    private String hot_policydata;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getCteate_time() {
        return cteate_time;
    }

    public void setCteate_time(Date cteate_time) {
        this.cteate_time = cteate_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getReport_day() {
        return report_day;
    }

    public void setReport_day(String report_day) {
        this.report_day = report_day;
    }

    public String getReport_week() {
        return report_week;
    }

    public void setReport_week(String report_week) {
        this.report_week = report_week;
    }

    public String getHot_weibo() {
        return hot_weibo;
    }

    public void setHot_weibo(String hot_weibo) {
        this.hot_weibo = hot_weibo;
    }

    public String getHot_all() {
        return hot_all;
    }

    public void setHot_all(String hot_all) {
        this.hot_all = hot_all;
    }

    public String getHot_search_terms() {
        return hot_search_terms;
    }

    public void setHot_search_terms(String hot_search_terms) {
        this.hot_search_terms = hot_search_terms;
    }

    public String getLeaders_PO() {
        return leaders_PO;
    }

    public void setLeaders_PO(String leaders_PO) {
        this.leaders_PO = leaders_PO;
    }

    public String getToday_PO_status() {
        return today_PO_status;
    }

    public void setToday_PO_status(String today_PO_status) {
        this.today_PO_status = today_PO_status;
    }

    public String getWarning_PO() {
        return warning_PO;
    }

    public void setWarning_PO(String warning_PO) {
        this.warning_PO = warning_PO;
    }

    public String getUpload_PO() {
        return upload_PO;
    }

    public void setUpload_PO(String upload_PO) {
        this.upload_PO = upload_PO;
    }

    public String getProject_po_status() {
        return project_po_status;
    }

    public void setProject_po_status(String project_po_status) {
        this.project_po_status = project_po_status;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getPush_PO() {
        return push_PO;
    }

    public void setPush_PO(String push_PO) {
        this.push_PO = push_PO;
    }

    public String getReprint_PO() {
        return reprint_PO;
    }

    public void setReprint_PO(String reprint_PO) {
        this.reprint_PO = reprint_PO;
    }

    public String getCollection_po() {
        return collection_po;
    }

    public void setCollection_po(String collection_po) {
        this.collection_po = collection_po;
    }

    public String getHot_wechat() {
        return hot_wechat;
    }

    public void setHot_wechat(String hot_wechat) {
        this.hot_wechat = hot_wechat;
    }

    public String getHot_douyin() {
        return hot_douyin;
    }

    public void setHot_douyin(String hot_douyin) {
        this.hot_douyin = hot_douyin;
    }

    public String getHot_bilibili() {
        return hot_bilibili;
    }

    public void setHot_bilibili(String hot_bilibili) {
        this.hot_bilibili = hot_bilibili;
    }

    public String getHot_tecentvedio() {
        return hot_tecentvedio;
    }

    public void setHot_tecentvedio(String hot_tecentvedio) {
        this.hot_tecentvedio = hot_tecentvedio;
    }

    public String getHot_36kr() {
        return hot_36kr;
    }

    public void setHot_36kr(String hot_36kr) {
        this.hot_36kr = hot_36kr;
    }

    public String getHot_finaceData() {
        return hot_finaceData;
    }

    public void setHot_finaceData(String hot_finaceData) {
        this.hot_finaceData = hot_finaceData;
    }

    public String getHot_policydata() {
        return hot_policydata;
    }

    public void setHot_policydata(String hot_policydata) {
        this.hot_policydata = hot_policydata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Synthesize that = (Synthesize) o;
        return Objects.equals(id, that.id) && Objects.equals(user_id, that.user_id) && Objects.equals(cteate_time, that.cteate_time) && Objects.equals(update_time, that.update_time) && Objects.equals(report_day, that.report_day) && Objects.equals(report_week, that.report_week) && Objects.equals(hot_weibo, that.hot_weibo) && Objects.equals(hot_all, that.hot_all) && Objects.equals(hot_search_terms, that.hot_search_terms) && Objects.equals(leaders_PO, that.leaders_PO) && Objects.equals(today_PO_status, that.today_PO_status) && Objects.equals(warning_PO, that.warning_PO) && Objects.equals(upload_PO, that.upload_PO) && Objects.equals(project_po_status, that.project_po_status) && Objects.equals(online, that.online) && Objects.equals(push_PO, that.push_PO) && Objects.equals(reprint_PO, that.reprint_PO) && Objects.equals(collection_po, that.collection_po) && Objects.equals(hot_wechat, that.hot_wechat) && Objects.equals(hot_douyin, that.hot_douyin) && Objects.equals(hot_bilibili, that.hot_bilibili) && Objects.equals(hot_tecentvedio, that.hot_tecentvedio) && Objects.equals(hot_36kr, that.hot_36kr) && Objects.equals(hot_finaceData, that.hot_finaceData) && Objects.equals(hot_policydata, that.hot_policydata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, cteate_time, update_time, report_day, report_week, hot_weibo, hot_all, hot_search_terms, leaders_PO, today_PO_status, warning_PO, upload_PO, project_po_status, online, push_PO, reprint_PO, collection_po, hot_wechat, hot_douyin, hot_bilibili, hot_tecentvedio, hot_36kr, hot_finaceData, hot_policydata);
    }

    @Override
    public String toString() {
        return "Synthesize{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", cteate_time=" + cteate_time +
                ", update_time=" + update_time +
                ", report_day='" + report_day + '\'' +
                ", report_week='" + report_week + '\'' +
                ", hot_weibo='" + hot_weibo + '\'' +
                ", hot_all='" + hot_all + '\'' +
                ", hot_search_terms='" + hot_search_terms + '\'' +
                ", leaders_PO='" + leaders_PO + '\'' +
                ", today_PO_status='" + today_PO_status + '\'' +
                ", warning_PO='" + warning_PO + '\'' +
                ", upload_PO='" + upload_PO + '\'' +
                ", project_po_status='" + project_po_status + '\'' +
                ", online='" + online + '\'' +
                ", push_PO='" + push_PO + '\'' +
                ", reprint_PO='" + reprint_PO + '\'' +
                ", collection_po='" + collection_po + '\'' +
                ", hot_wechat='" + hot_wechat + '\'' +
                ", hot_douyin='" + hot_douyin + '\'' +
                ", hot_bilibili='" + hot_bilibili + '\'' +
                ", hot_tecentvedio='" + hot_tecentvedio + '\'' +
                ", hot_36kr='" + hot_36kr + '\'' +
                ", hot_finaceData='" + hot_finaceData + '\'' +
                ", hot_policydata='" + hot_policydata + '\'' +
                '}';
    }
}