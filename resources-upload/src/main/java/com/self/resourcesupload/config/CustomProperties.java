package com.self.resourcesupload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

}
