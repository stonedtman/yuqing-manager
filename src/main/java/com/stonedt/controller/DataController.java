package com.stonedt.controller;

import com.stonedt.service.DataService;
import com.stonedt.util.ResultUtil;
import com.stonedt.vo.DataChartVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
