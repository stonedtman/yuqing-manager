package com.stonedt.controller;

import com.github.pagehelper.PageInfo;
import com.stonedt.service.DataService;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.ArticleVO;
import com.stonedt.vo.DataChartVO;
import com.stonedt.vo.DataRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 文轩
 */
@RestController
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * 获取数据图表
     */
    @GetMapping("/getDataChart")
    public ResultUtil<DataChartVO> getDataChart(@RequestParam(required = false) String sourceWebsite) {
        return dataService.getDataChart(sourceWebsite);
    }

    /**
     * 获取数据来源列表
     */
    @GetMapping("/getDataSources")
    public ResultUtil<List<String>> getDataSources() {
        return ResultUtil.ok(dataService.getDataSources(null));
    }

    /**
     * 数据来源图表
     */
    @GetMapping("/getDataSourcesChart")
    public ResultUtil<List<DataRecord>> getDataSourcesChart(@RequestParam("days") Integer days,
                                                            @RequestParam(required = false) String sourceWebsite) {
        return dataService.getDataSourcesChart(days,sourceWebsite);
    }

    /**
     * 文章数据列表
     */
    @GetMapping("/getArticleList")
    public ResultUtil<PageInfo<ArticleVO>> getArticleList(@RequestParam String sourceWebsite,
                                                          @RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestParam(defaultValue = "10") Integer pageSize) {
        return dataService.getArticleList(sourceWebsite, pageNum, pageSize);
    }



}
