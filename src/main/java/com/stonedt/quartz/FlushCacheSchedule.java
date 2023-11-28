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

    @Scheduled(fixedDelay = 1000 * 60 * 10)
    public void flushCache() {
        Cache dataSourcesChart = cacheManager.getCache("dataSourcesChart");

        if (dataSourcesChart != null) {
            dataSourcesChart.clear();
        }
        Cache dataSources = cacheManager.getCache("dataSources");
        if (dataSources != null) {
            dataSources.clear();
        }
        dataService.getDataSourcesChart(0,null);
        dataService.getDataSourcesChart(1,null);
        dataService.getDataSourcesChart(3,null);
        dataService.getDataSourcesChart(15,null);
        dataService.getDataSourcesChart(30,null);
    }
}
