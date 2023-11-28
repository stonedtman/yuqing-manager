package com.stonedt.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.stonedt.service.DataService;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.ArticleVO;
import com.stonedt.vo.DataChartVO;
import com.stonedt.vo.DataRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 数据服务
 * @author 文轩
 */
@Service
public class DataServiceImpl implements DataService {

    private final static String[] DATA_SOURCES = {
            "美通社",
            "学习强国",
            "河南省人民政府",
            "宿迁市发展和改革委员会",
            "上海市人民政府",
            "华人号",
            "龙讯财经",
            "百度贴吧",
            "知乎问题",
            "海南省人民政府",
            "微信公众号",
            "云掌号",
            "抖音",
            "今日头条",
            "每日经济新闻",
            "人民智作",
            "镇江市工业和信息化局",
            "南通市工业和信息化局",
            "新浪博客",
            "安徽网",
            "黑猫投诉",
            "ZAKER",
            "南京晨报",
            "爱咖号",
            "股吧",
            "武汉城市留言板",
            "生意社",
            "人民网",
            "百度百家",
            "手机新浪网",
            "光明网",
            "至诚财经网",
            "海南网",
            "同花顺财经",
            "怀化新闻网",
            "宿迁市住房和城乡建设局",
            "新浪",
            "百家号",
            "小红书",
            "安徽财经网",
            "深圳新闻网",
            "陕西省人民政府",
            "网易号",
            "和讯网",
            "和讯股票",
            "搜狐新闻",
            "新浪财经",
            "南京日报",
            "搜狐号",
            "新浪微博",
            "好看视频",
            "中国新闻网",
            "一点资讯",
            "中国国际招标网",
            "东方号",
            "快手",
            "雪球",
            "豆瓣",
            "证券之星",
            "太湖新闻网",
            "淮安市住房和城乡建设局",
            "北京市人民政府",
            "连云港市住房和城乡建设局",
            "度小视",
            "微视",
            "万隆证券网",
            "江苏-网易",
            "中国财经信息网",
            "知乎问答",
            "全国党媒信息公共平台",
            "南通市科技局",
            "果壳",
            "新浪长微博",
            "无锡市市场监督管理局",
            "澎湃新闻",
            "经济观察网",
            "老辰光网",
            "东方财富网",
            "南方财富网",
            "微博",
            "宿迁市市场监督管理局",
            "bilibili",
            "ZAKER资讯",
            "北极星风机发电网",
            "中国怀化市政府公众信息网",
            "微信",
            "新华报业网(交汇点新闻网",
            "无锡市工业和信息化局",
            "闪电新闻"
    };

    @Value("${es.search.url}")
    private String esSearchUrl;

    private final RestTemplate restTemplate;

    private final SimpleDateFormat ymd;

    private final SimpleDateFormat ymdhms;

    public DataServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.ymd = new SimpleDateFormat("yyyy-MM-dd");
        this.ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            if (i == 6) {
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
        DataRecord dataRecord = getDataCountByDataSources(sourceWebsite,null);
        if (dataRecord == null) {
            return ResultUtil.build(500,"获取数据失败",null);
        }
        dataChartVO.setDataCount(dataRecord.getCount());

        return ResultUtil.build(200,"获取数据成功",dataChartVO);
    }


    /**
     * 根据数据来源获取信息
     */
    private DataRecord getDataCountByDataSources(String dataSources,String times) {
        String url = esSearchUrl + "/yqsearch/searchlist?searchType=1";
        if (dataSources != null) {
            url += "&sourceWebsite=" + dataSources;
        }
        if (times != null && !"".equals(times)) {
            url += "&times=" + times;
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
//        dataRecord.setId(data.getString("_id"));
        dataRecord.setSource(dataSources);
        return dataRecord;
    }


    /**
     * 根据数据来源获取最新抓取时间
     */
    private String getLastSpiderTimeByDataSources(String dataSources) {
        String url = esSearchUrl + "/yqsearch/searchlist?searchType=5";
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

    /**
     * 获取数据来源列表
     */
    @Cacheable(value = "dataSources",key = "#times")
    public List<String> getDataSources(String times) {
        String url = esSearchUrl + "/yqsearch/datasourceanalysis";
        if (times != null) {
            url += "?times=" + times;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        String result = response.getBody();
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray buckets = jsonObject.getJSONObject("aggregations")
                .getJSONObject("group_by_tags")
                .getJSONArray("buckets");
        Set<String> dataSourceSet = new HashSet<>();
        for (int i = 0; i < buckets.size(); i++) {
            JSONObject bucket = buckets.getJSONObject(i);
            JSONArray jsonArray = bucket.getJSONObject("group_by_tags")
                    .getJSONArray("buckets");
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                String key = jsonObject1.getString("key");
                dataSourceSet.add(key);
            }

        }
        return new ArrayList<>(dataSourceSet);
    }

    @Override
    @Cacheable(value = "dataSourcesChart",key = "#days+'_'+#sourceWebsite")
    public ResultUtil<List<DataRecord>> getDataSourcesChart(Integer days, String sourceWebsite) {
        final String times;
        if (days != null && days > 0) {
            long currentTimeMillis = System.currentTimeMillis();
            Date start = new Date(currentTimeMillis - TimeUnit.DAYS.toMillis(days));
            times = ymdhms.format(start);
        } else {
            times = null;
        }
        List<DataRecord> dataRecordList = new ArrayList<>();
        List<String> sourceList = getDataSources(times);
        final List<String> dataSourceList;
        if (sourceWebsite != null && !sourceWebsite.trim().isEmpty()) {
            //模糊匹配
            dataSourceList = new ArrayList<>();
            sourceList.stream().filter(s -> s.contains(sourceWebsite)).forEach(dataSourceList::add);
        }else {
            dataSourceList = sourceList;
        }
        //创建任务列表
        List<CompletableFuture<Void>> taskList = new ArrayList<>();


        for (String dataSource : dataSourceList) {
            CompletableFuture<Void> task = CompletableFuture.runAsync(()-> {
                        DataRecord dataRecord = getDataCountByDataSources(dataSource,times);
                        if (dataRecord == null) {
                            return;
                        }
                        dataRecord.setLastSpiderTime(getLastSpiderTimeByDataSources(dataSource));
                        dataRecordList.add(dataRecord);
                    }
            );
            taskList.add(task);
        }
        CompletableFuture.allOf(taskList.toArray(new CompletableFuture[taskList.size()])).join();
        // 按照数据量排序
        dataRecordList.sort((o1, o2) -> o2.getCount() - o1.getCount());


        return ResultUtil.build(200, "获取数据成功", dataRecordList);
    }

    @Override
    @Cacheable(value = "articleList",key = "#sourceWebsite+'_'+#pageNum+'_'+#pageSize")
    public ResultUtil<PageInfo<ArticleVO>> getArticleList(String sourceWebsite, Integer pageNum, Integer pageSize) {
        String url = esSearchUrl + "/yqsearch/searchlist?searchType=2&page="+pageNum;
        if (sourceWebsite != null) {
            url += "&sourceWebsite=" + sourceWebsite;
        }
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
        PageInfo<ArticleVO> pageInfo =new PageInfo<>();
        pageInfo.setTotal(jsonObject.getInteger("count"));
        pageInfo.setPages(jsonObject.getInteger("page_count"));
        pageInfo.setPageNum(jsonObject.getInteger("page"));
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<ArticleVO> articleVOList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            JSONObject source = data.getJSONObject("_source");
            ArticleVO articleVO = new ArticleVO();
            articleVO.setId(data.getString("_id"));
            articleVO.setTitle(source.getString("title"));
            articleVO.setAuthor(source.getString("author"));
            articleVO.setSpiderTime(source.getString("spider_time"));
            articleVO.setPublishTime(source.getString("publish_time"));
            articleVOList.add(articleVO);
        }
        pageInfo.setList(articleVOList);
        return ResultUtil.build(200,"获取数据成功",pageInfo);
    }
}
