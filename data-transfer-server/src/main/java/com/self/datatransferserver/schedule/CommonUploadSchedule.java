package com.self.datatransferserver.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.self.datatransferserver.config.CustomProperties;
import com.self.datatransferserver.util.FileUtil;
import com.self.datatransferserver.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

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
        //遍历接收文件目录
        File[] totalFiles = new File(customProperties.getDirRoot() + customProperties.getDirTemp()).listFiles();
        List<File> list = Arrays.asList(totalFiles);

        if(list != null && list.size() > 0){
            list.stream().forEach(file -> {
                JSONArray array = JsonUtil.readJsonFile(file, StandardCharsets.UTF_8);

                //解析失败则删除文件
                if(array.size() < 1){
                    file.delete();
                    return;
                }

                for (Object obj : array) {
                    if(obj instanceof JSONObject){
                        JSONObject jobj = (JSONObject)obj;

                        //TODO--查询数据库判断数据是否已经保存,若未保存则保存数据并移动文件到保存成功目录,若已保存则直接删除文件

                    }
                }
            });
        }
    }

}
