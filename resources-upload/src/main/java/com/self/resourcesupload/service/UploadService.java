package com.self.resourcesupload.service;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

/**
 * 上传 Service
 */
public interface UploadService {

    /**
     * 上传文件
     * @param file 上传文件
     * @param extParam 扩展参数
     * @return 上传返回信息
     */
    Map upload(File file, String...extParam);

    /**
     * 下载文件
     * @param downloadPath 文件下载路径
     * @param fileInfo 文件信息
     */
    void download(Path downloadPath, String...fileInfo);

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
