package com.self.datatransferserver.config;

import com.self.resourcesupload.base.BaseUploadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 启动初始化
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {

    @Autowired
    private CustomProperties customProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化Oss
        ossInit();

        System.out.println("启动成功");
    }

    /**
     * 初始化Oss
     */
    private void ossInit(){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("endpoint", customProperties.getEndPoint());
        paramMap.put("accessKeyId", customProperties.getAccessKeyId());
        paramMap.put("accessKeySecret", customProperties.getAccessKeySecret());
        paramMap.put("bucketName", customProperties.getBucketName());
        paramMap.put("stealToken", customProperties.isStealToken());

        BaseUploadFactory.init("OSS", paramMap);
    }

}
