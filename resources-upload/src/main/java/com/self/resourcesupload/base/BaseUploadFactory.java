package com.self.resourcesupload.base;

import com.self.resourcesupload.impl.OssUploadServiceImpl;
import com.self.resourcesupload.service.UploadService;

import java.util.Map;

/**
 * 文件上传工厂
 */
public class BaseUploadFactory {

    /**
     * OSS
     */
    private static final String OSS = "OSS";

    /**
     * 文件类型
     */
    private static String fileType = null;

    /**
     * 配置参数
     */
    private static Map configMap;

    /**
     * 文件上传工厂配置参数初始化
     * @param fileType 文件类型
     * @param configMap 配置参数
     * @return UploadService
     */
    public static UploadService init(String fileType, Map configMap){
        synchronized (BaseUploadFactory.class){
            if(BaseUploadFactory.fileType == null){
                BaseUploadFactory.fileType = fileType;
            }

            if(BaseUploadFactory.configMap == null){
                BaseUploadFactory.configMap = configMap;
            }
        }

        return getInstance();
    }

    /**
     * 获取指定文件类型单实例
     * @return UploadService
     */
    public static UploadService getInstance(){
        if(OSS.equalsIgnoreCase(fileType)){
            return OssSingletonHolder.ossUploader;
        }else{
            return null;
        }
    }

    /**
     * OssSingleton
     */
    private static class OssSingletonHolder{
        private static final UploadService ossUploader = new OssUploadServiceImpl(configMap);
    }

}
