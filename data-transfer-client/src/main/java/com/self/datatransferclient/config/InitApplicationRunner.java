package com.self.datatransferclient.config;

import com.self.datatransferclient.schedule.CommonUploadSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动初始化
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {

    @Autowired
    private CommonUploadSchedule commonUploadSchedule;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //启动定时任务向服务器上传文件
        commonUploadSchedule.deleteTransferTestJob();
        commonUploadSchedule.startTransferTestJob();

        System.out.println("启动成功");
    }

}
