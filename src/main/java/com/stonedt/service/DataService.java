package com.stonedt.service;

import com.stonedt.util.ResultUtil;
import com.stonedt.vo.DataChartVO;

import java.util.List;

/**
 * @author 文轩
 */
public interface DataService {
    ResultUtil<DataChartVO> getDataChart(String dataSources);

}
