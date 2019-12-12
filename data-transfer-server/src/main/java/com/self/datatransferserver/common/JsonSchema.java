package com.self.datatransferserver.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.self.datatransferserver.config.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Json Schema操作类
 */
@Slf4j
@Component
@Scope("singleton")
public class JsonSchema {

    @Autowired
    private CustomProperties customProperties;

    /**
     * 校验json数据格式
     * @param schemaFileName schema文件名
     * @param jsonData json数据字符串
     * @return 校验结果
     */
    public boolean validateJsonByFge(String schemaFileName, String jsonData){
        JsonNode dataJsonNode = null;
        JsonNode schemaJsonNode = null;
        ProcessingReport processingReport = null;

        try {
            //json数据
            dataJsonNode = JsonLoader.fromString(jsonData);

            //schema文件路径
            String schemaFilePath = JsonSchema.class.getClassLoader().getResource(customProperties.getSchemaPath() + "/" + schemaFileName).getPath();

            //路径字符特殊处理
            schemaFilePath = StringUtils.replace(StringUtils.substring(schemaFilePath, 1), "%20", " ");

            schemaJsonNode = new JsonNodeReader().fromReader(new FileReader(Paths.get(schemaFilePath).toFile()));
        } catch (IOException e) {
            log.error("json校验异常");
            return false;
        }

        processingReport = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaJsonNode, dataJsonNode);

        if(processingReport.isSuccess()){
            return true;
        }else {
            StringBuilder builder = new StringBuilder();
            Iterator<ProcessingMessage> iterator = processingReport.iterator();
            while(iterator.hasNext()){
                builder.append(iterator.next());
            }

            log.error("校验json数据失败：{}", builder.toString());

            return false;
        }
    }

}
