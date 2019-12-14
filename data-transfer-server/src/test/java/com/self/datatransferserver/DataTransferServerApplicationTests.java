package com.self.datatransferserver;

import com.self.datatransferserver.config.CustomProperties;
import com.self.resourcesupload.base.BaseUploadFactory;
import com.self.resourcesupload.service.UploadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class DataTransferServerApplicationTests {

    @Autowired
    private CustomProperties customProperties;

    @Test
    void contextLoads() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("endpoint", customProperties.getEndPoint());
        paramMap.put("accessKeyId", customProperties.getAccessKeyId());
        paramMap.put("accessKeySecret", customProperties.getAccessKeySecret());
        paramMap.put("bucketName", customProperties.getBucketName());
        paramMap.put("stealToken", customProperties.isStealToken());

        BaseUploadFactory.init("OSS", paramMap);
        UploadService service = BaseUploadFactory.getInstance();

        //文件上传
        File file = new File("C:\\pic\\front\\20190926205958.jpg");
        service.upload(file, new String[0]);

        //文件下载
        //String[] arr = new String[2];
        //arr[0] = "zpsbucket";
        //arr[1] = "050b406079894125ae91f5f898128a86";
        //Path downloadPath = Paths.get("C:\\pic\\" + arr[1] + ".jpg");
        //service.download(downloadPath, arr);

        //获取下载连接
        //String[] arr = new String[2];
        //arr[0] = "zpsbucket";
        //arr[1] = "050b406079894125ae91f5f898128a86";
        //String downloadUrl = service.getDownloadUrl(arr[0], arr[1]);

        //删除上传文件
        //String[] arr = new String[2];
        //arr[0] = "zpsbucket";
        //arr[1] = "050b406079894125ae91f5f898128a86";
        //service.deleteFile(arr[0], arr[1]);
    }

}
