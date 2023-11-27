package com.stonedt.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.service.DataService;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.DataChartVO;
import com.stonedt.vo.DataRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据服务
 * @author 文轩
 */
@Service
public class DataServiceImpl implements DataService {

    @Value("${es.search.url}")
    private String esSearchUrl;

    private final RestTemplate restTemplate;

    private final SimpleDateFormat ymd;

    public DataServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.ymd = new SimpleDateFormat("yyyy-MM-dd");
    }


    @Override
    public ResultUtil<DataChartVO> getDataChart(String sourceWebsite) {
        final DataChartVO dataChartVO = new DataChartVO();
        List<DataChartVO.DataChartDay> dataChartDayVOList = new ArrayList<>();

        Date weekAgo = new Date(System.currentTimeMillis() - 6 * 24 * 60 * 60 * 1000);
        int weekCount = 0;
        for (int i = 0; i < 7; i++) {
            String date = ymd.format(weekAgo);
            String times = date + " 00:00:00";
            String timee = date + " 23:59:59";
            String url = esSearchUrl + "/yqsearch/searchlist" + "?times=" + times + "&timee=" + timee;
            if (sourceWebsite != null) {
                url += "&sourceWebsite=" + sourceWebsite;
            }
            System.out.println(url);
            DataChartVO.DataChartDay dataChartDay = new DataChartVO.DataChartDay();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json;charset=UTF-8");

            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
            String result = response.getBody();
            JSONObject jsonObject = JSON.parseObject(result);
            String code = jsonObject.getString("code");
            if (!"200".equals(code)) {
                return ResultUtil.build(500,"获取数据失败",null);
            }
            Integer count = jsonObject.getInteger("count");
            if (i == 0) {
                dataChartVO.setTodayDataCount(count);
            }
            weekCount += count;
            dataChartDay.setDate(date);
            dataChartDay.setCount(count);
            dataChartDayVOList.add(dataChartDay);
            weekAgo.setTime(weekAgo.getTime() + 24 * 60 * 60 * 1000);
        }
        dataChartVO.setDataChartDayVOList(dataChartDayVOList);
        dataChartVO.setWeekDataCount(weekCount);
        DataRecord dataRecord = getDataCountByDataSources(sourceWebsite);
        if (dataRecord == null) {
            return ResultUtil.build(500,"获取数据失败",null);
        }
        dataChartVO.setDataCount(dataRecord.getCount());

        return ResultUtil.build(200,"获取数据成功",dataChartVO);
    }


    /**
     * 根据数据来源获取信息
     */
    private DataRecord getDataCountByDataSources(String dataSources) {
        String url = esSearchUrl + "/yqsearch/searchlist?searchType=1";
        if (dataSources != null) {
            url += "&sourceWebsite=" + dataSources;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        String result = response.getBody();
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if (!"200".equals(code)) {
            return null;
        }
        Integer count = jsonObject.getInteger("count");
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONObject data = jsonArray.getJSONObject(0);
        JSONObject source = data.getJSONObject("_source");
        DataRecord dataRecord = new DataRecord();
        dataRecord.setCount(count);
        dataRecord.setLastPublishTime(source.getString("publish_time"));
        dataRecord.setId(data.getString("_id"));
        dataRecord.setSource(dataSources);
        return dataRecord;
    }


    /**
     * 根据数据来源获取最新抓取时间
     */
    private String getLastSpiderTimeByDataSources(String dataSources) {
        String url = esSearchUrl + "/yqsearch/searchlist?searchType=1";
        if (dataSources != null) {
            url += "&sourceWebsite=" + dataSources;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        String result = response.getBody();
        JSONObject jsonObject = JSON.parseObject(result);
        String code = jsonObject.getString("code");
        if (!"200".equals(code)) {
            return null;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONObject data = jsonArray.getJSONObject(0);
        JSONObject source = data.getJSONObject("_source");
        return source.getString("spider_time");
    }
}
