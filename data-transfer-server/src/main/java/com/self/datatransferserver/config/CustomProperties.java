package com.self.datatransferserver.config;

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

}
