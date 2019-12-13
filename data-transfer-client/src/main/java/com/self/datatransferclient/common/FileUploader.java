package com.self.datatransferclient.common;

import com.self.datatransferclient.config.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import net.dongliu.requests.RequestBuilder;
import net.dongliu.requests.Requests;
import net.dongliu.requests.body.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传
 */
@Slf4j
@Component
@Scope("singleton")
public class FileUploader {

    @Autowired
    private CustomProperties customProperties;

    /**
     * 上传文件到服务器
     * @param url 上传url
     * @param fileKey 上传二进制文件key
     * @param file 上传文件
     * @return 响应状态码
     */
    public int uploadFileToServer(String url, String fileKey, File file){
        //设置header
        Map<String, Object> headers = new HashMap<>();
        headers.put("filename", file.getName());

        RequestBuilder RequestBuilder = Requests.post(url).headers(headers);

        //设置body
        RequestBuilder.multiPartBody(
                Part.text("version", "1.0"),
                Part.file(fileKey, file)
        );

        return RequestBuilder.send().getStatusCode();
    }

}
