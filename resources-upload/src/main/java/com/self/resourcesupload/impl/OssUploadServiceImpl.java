package com.self.resourcesupload.impl;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.self.resourcesupload.service.UploadService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Oss上传业务实现
 */
public class OssUploadServiceImpl implements UploadService {

    /**
     * 初始化配置参数
     */
    private Map configMap;

    /**
     * oss客户端
     */
    private OSS ossClient;

    /**
     * bucketName
     */
    private String bucketName;

    /**
     * oss实例化创建ossClient
     * @param configMap 配置参数
     */
    public OssUploadServiceImpl(Map configMap){
        this.configMap = configMap;

        //获取参数
        String endPoint = getParam(configMap, "endpoint", "");
        String accessKeyId = getParam(configMap, "accessKeyId", "");
        String accessKeySecret = getParam(configMap, "accessKeySecret", "");
        this.bucketName = getParam(configMap, "bucketName", "");

        //初始化ossClient
        ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        if(ossClient.doesBucketExist(bucketName)){
            //若容器不存在则创建容器
            ossClient.createBucket(bucketName);
        }
    }

    /**
     * 文件上传
     * @param fileContent 文件内容
     * @param extParam 扩展参数
     * @return 上传返回参数
     */
    @Override
    public Map upload(byte[] fileContent, String... extParam) {
        Map<String, Object> resultMap = new HashMap<>();
        String uid = UUID.randomUUID().toString().replace("-", "");

        try {
            ossClient.putObject(this.bucketName, uid, new ByteArrayInputStream(fileContent));
        }catch (Exception e){
            throw new RuntimeException("文件上传失败");
        }

        resultMap.put("fileId", uid);
        return resultMap;
    }

    /**
     * 文件下载
     * @param fileInfo 文件信息(fileInfo[0]--bucketName,fileInfo[1]--fileKey)
     * @return 下载文件字节数组
     */
    @Override
    public byte[] download(String...fileInfo) {
        OSSObject oSSObject = ossClient.getObject(fileInfo[0], fileInfo[1]);
        InputStream objectContent = oSSObject.getObjectContent();

        try {
            if(objectContent.available() == 0){
                return new byte[0];
            }

            byte[] data = new byte[objectContent.available()];
            objectContent.read(data);
            objectContent.close();
            return data;
        } catch (IOException e) {
            throw new RuntimeException("文件下载失败");
        }
    }

    /**
     * 删除文件
     * @param fileInfo 文件信息(fileInfo[0]--bucketName,fileInfo[1]--fileKey)
     */
    @Override
    public void deleteFile(String...fileInfo) {
        ossClient.deleteObject(fileInfo[0], fileInfo[1]);
    }

    /**
     * 获取下载链接
     * @param fileInfo 文件信息(fileInfo[0]--bucketName,fileInfo[1]--fileKey)
     * @return 文件下载连接
     */
    @Override
    public String getDownloadUrl(String...fileInfo) {
        boolean stealToken = getParam(this.configMap, "stealToken", false);
        if(stealToken){
            Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(fileInfo[0], fileInfo[1], HttpMethod.GET);
            req.setExpiration(expiration);
            URL signedUrl = ossClient.generatePresignedUrl(req);
            return signedUrl.toString();
        }else{
            String endPoint = getParam(this.configMap, "endpoint", "");
            return endPoint.replace("http://","http://" + fileInfo[0] + ".") + fileInfo[1];
        }
    }

    /**
     * 获取配置参数
     * @param configMap 参数映射
     * @param key key
     * @param defaultValue 默认值
     * @param <T>
     * @return 配置参数值
     */
    private <T> T getParam(Map configMap, String key, T defaultValue){
         Object obj = configMap.get(key);
         if(obj == null){
            return defaultValue;
         }else{
            return (T)obj;
         }
    }

}
