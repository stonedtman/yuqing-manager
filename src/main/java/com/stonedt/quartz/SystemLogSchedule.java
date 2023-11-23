package com.stonedt.quartz;

import com.stonedt.dao.UserDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 系统日志定时任务
 */
@Component
public class SystemLogSchedule {

    private final UserDao userDao;


    public SystemLogSchedule(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 每天凌晨0点执行一次
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void systemLogSchedule() {
        //获取当前时间的前180日的时间
        userDao.deleteSystemlogWhenLessTime(new Date(System.currentTimeMillis() - 180L * 24 * 60 * 60 * 1000));
    }
}
