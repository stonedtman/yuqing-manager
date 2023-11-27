package com.stonedt.controller;

import com.stonedt.service.DataService;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.DataChartVO;
import com.stonedt.vo.DataRecord;
import org.apache.ibatis.annotations.Param;
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
    @RequestMapping("/getDataChart")
    public ResultUtil<DataChartVO> getDataChart(@RequestParam(required = false) String sourceWebsite) {
        return dataService.getDataChart(sourceWebsite);
    }

    /**
     * 获取数据来源列表
     */
    @RequestMapping("/getDataSources")
    public ResultUtil<List<String>> getDataSources() {
        return ResultUtil.ok(dataService.getDataSources(null));
    }

    /**
     * 数据来源图表
     */
    @RequestMapping("/getDataSourcesChart")
    public ResultUtil<List<DataRecord>> getDataSourcesChart(@RequestParam("days") Integer days,
                                                            @RequestParam(required = false) String sourceWebsite) {
        return dataService.getDataSourcesChart(days,sourceWebsite);
    }



}
