package com.self.resourcesupload;

import com.self.resourcesupload.base.BaseUploadFactory;
import com.self.resourcesupload.config.CustomProperties;
import com.self.resourcesupload.service.UploadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ResourcesUploadApplicationTests {

    @Autowired
    private CustomProperties customProperties;

    @Test
    void contextLoads() throws IOException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("endpoint", customProperties.getEndpoint());
        paramMap.put("accessKeyId", customProperties.getAccessKeyId());
        paramMap.put("accessKeySecret", customProperties.getAccessKeySecret());
        paramMap.put("bucketName", customProperties.getBucketName());
        paramMap.put("stealToken", customProperties.isStealToken());

        BaseUploadFactory.init("OSS", paramMap);
        UploadService service = BaseUploadFactory.getInstance();

        //文件上传
        //File file = new File("C:\\pic\\front\\20190926205958.jpg");
        //service.upload(file, new String[0]);

        //文件下载
        //String[] arr = new String[2];
        //arr[0] = "zpsbucket";
        //arr[1] = "050b406079894125ae91f5f898128a86";
        //Path downloadPath = Paths.get("C:\\pic\\" + arr[1] + ".jpg");
        //service.download(downloadPath, arr);


    }

}
