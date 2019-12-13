package com.self.datatransferserver.config;

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
     * schema文件根路径
     */
    private String schemaPath;

    /**
     * 文件根目录
     */
    private String dirRoot;

    /**
     * 文件临时接收目录
     */
    private String dirTemp;

    /**
     * 文件保存成功目录
     */
    private String dirSaveSuccess;

}
