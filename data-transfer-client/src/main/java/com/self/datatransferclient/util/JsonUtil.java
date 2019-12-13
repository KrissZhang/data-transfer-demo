package com.self.datatransferclient.util;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.Charset;

/**
 * Json工具类
 */
@Slf4j
public class JsonUtil {

    /**
     * 读取Json文件数据
     * @param file json文件
     * @param charset 字符集
     * @return json数据列表
     */
    public static JSONArray readJsonFile(File file, Charset charset){
        byte[] bytes = FileUtil.readAllBytes(file.toPath());

        if(bytes != null && bytes.length > 0){
            String jsonData = new String(bytes, charset);
            return JSONArray.parseArray(jsonData);
        }else {
            return new JSONArray();
        }
    }

}
