package com.self.resourcesupload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义配置
 */
@Data
@Component
@Scope("singleton")
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 地域节点
     */
    private String endpoint;

    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * bucketName
     */
    private String bucketName;

    /**
     * 是否防盗链
     */
    private boolean stealToken;

    /**
     * 获取OSS配置参数
     * @return OSS配置映射
     */
    public Map<String, Object> getOssParamMap(){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("endpoint", endpoint);
        paramMap.put("accessKeyId", accessKeyId);
        paramMap.put("accessKeySecret", accessKeySecret);
        paramMap.put("bucketName", bucketName);
        paramMap.put("stealToken", stealToken);

        return paramMap;
    }

}
