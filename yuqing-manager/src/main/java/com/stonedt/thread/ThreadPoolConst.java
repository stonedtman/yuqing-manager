package com.stonedt.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池常量
 * @author 文轩
 */
public class ThreadPoolConst {

    /**
     * io线程池
     */
    public static final ExecutorService IO_EXECUTOR = new ThreadPoolExecutor(8,16,11, TimeUnit.MINUTES,new LinkedBlockingQueue<Runnable>());

}
