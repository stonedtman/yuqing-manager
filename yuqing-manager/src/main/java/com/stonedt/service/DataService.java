package com.stonedt.service;

import com.github.pagehelper.PageInfo;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.ArticleVO;
import com.stonedt.vo.DataChartVO;
import com.stonedt.vo.DataRecord;

import java.util.List;

/**
 * @author 文轩
 */
public interface DataService {
    ResultUtil<DataChartVO> getDataChart(String dataSources);

    List<String> getDataSources(String times);

    ResultUtil<List<DataRecord>> getDataSourcesChart(Integer days, String sourceWebsite);

    ResultUtil<PageInfo<ArticleVO>> getArticleList(String sourceWebsite,Integer pageNum,Integer pageSize);
}
