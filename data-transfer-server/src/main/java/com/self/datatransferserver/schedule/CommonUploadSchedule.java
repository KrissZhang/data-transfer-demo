package com.self.datatransferserver.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 通用上传文件定时处理任务
 */
@Component
@EnableScheduling
public class CommonUploadSchedule {

    /**
     * 每一分钟执行一次定时任务
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void sync(){

    }

}
