package com.self.resourcesupload.service;

import java.util.Map;

/**
 * 上传 Service
 */
public interface UploadService {

    /**
     * 上传文件
     * @param fileContent 文件内容
     * @param extParam 扩展参数
     * @return 上传返回信息
     */
    Map upload(byte[] fileContent, String...extParam);

    /**
     * 下载文件
     * @param fileInfo 文件信息
     * @return 下载文件字节数组
     */
    byte[] download(String...fileInfo);

    /**
     * 删除文件
     * @param fileInfo 文件信息
     */
    void deleteFile(String...fileInfo);

    /**
     * 获取下载url
     * @param fileInfo 文件信息
     * @return 下载url
     */
    String getDownloadUrl(String...fileInfo);

}
