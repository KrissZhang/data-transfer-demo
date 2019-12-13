package com.self.datatransferclient.schedule.execute;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务执行逻辑类
 */
@Slf4j
@DisallowConcurrentExecution
public class TransferTestJobLogic implements Job {

    /**
     * 定时任务逻辑实现
     * @param jobExecutionContext jobExecutionContext
     * @throws JobExecutionException JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("执行了!!!");
    }

}
