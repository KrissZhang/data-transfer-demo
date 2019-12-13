package com.self.datatransferclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自定义配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * schema文件根路径
     */
    private String schemaPath;

    /**
     * 文件根目录
     */
    private String dirRoot;

    /**
     * 文件待上传目录
     */
    private String dirUpload;

    /**
     * 文件上传成功目录
     */
    private String dirUploadSuccess;

    /**
     * 单个文件最大数据条数
     */
    private Integer dataMaxSize;

    /**
     * 客户端发送者
     */
    private String clientSender;

    /**
     * 请求服务地址
     */
    private String reqServerAddr;

}
