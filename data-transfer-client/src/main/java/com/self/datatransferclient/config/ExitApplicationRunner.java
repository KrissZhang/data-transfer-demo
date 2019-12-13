package com.self.datatransferclient.config;

import com.self.datatransferclient.schedule.CommonUploadSchedule;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * 服务器停止清理
 */
@Component
public class ExitApplicationRunner {

    @Autowired
    private CommonUploadSchedule commonUploadSchedule;

    /**
     * 停止服务时执行
     */
    @PreDestroy
    public void destory() throws SchedulerException {
        //停止定时上传文件任务
        commonUploadSchedule.deleteTransferTestJob();

        System.out.println("服务停止结束");
    }

}
