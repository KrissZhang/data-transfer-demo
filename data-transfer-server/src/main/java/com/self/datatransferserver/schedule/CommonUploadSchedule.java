package com.self.datatransferserver.schedule;

import com.self.datatransferserver.config.CustomProperties;
import com.self.datatransferserver.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * 通用上传文件定时处理任务
 */
@Slf4j
@Component
@EnableScheduling
public class CommonUploadSchedule {

    @Autowired
    private CustomProperties customProperties;

    /**
     * 初始化目录信息
     */
    @PostConstruct
    public void init(){
        //初始化临时接收目录
        File tempFile = new File(customProperties.getDirRoot() + customProperties.getDirTemp());
        if(!tempFile.exists()){
            FileUtil.mkdirs(tempFile.toPath());
        }

        //文件保存成功目录
        File saveSuccessFile = new File(customProperties.getDirRoot() + customProperties.getDirSaveSuccess());
        if(!saveSuccessFile.exists()){
            FileUtil.mkdirs(saveSuccessFile.toPath());
        }
    }

    /**
     * 每一分钟执行一次定时任务
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void sync(){

    }

}
