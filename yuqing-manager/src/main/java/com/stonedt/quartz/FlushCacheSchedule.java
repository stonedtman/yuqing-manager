package com.stonedt.quartz;

import com.stonedt.service.DataService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时刷新缓存
 * @author 文轩
 */
@Component
public class FlushCacheSchedule {

    private final DataService dataService;

    private final CacheManager cacheManager;

    public FlushCacheSchedule(DataService dataService,
                              CacheManager cacheManager) {
        this.dataService = dataService;
        this.cacheManager = cacheManager;
    }

    @Scheduled(fixedDelay = 1000L * 60 * 60 * 2)
    public void flushCache() {
        System.out.println("------------------刷新缓存任务开始---------------");
        Cache dataSourcesChart = cacheManager.getCache("dataSourcesChart");

        if (dataSourcesChart != null) {
            dataSourcesChart.clear();
        }
        Cache dataSources = cacheManager.getCache("dataSources");
        if (dataSources != null) {
            dataSources.clear();
        }
        dataService.getDataSourcesChart(0,"");
        dataService.getDataSourcesChart(1,"");
        dataService.getDataSourcesChart(3,"");
        dataService.getDataSourcesChart(15,"");
        dataService.getDataSourcesChart(30,"");
        System.out.println("------------------刷新缓存任务结束---------------");
    }
}
