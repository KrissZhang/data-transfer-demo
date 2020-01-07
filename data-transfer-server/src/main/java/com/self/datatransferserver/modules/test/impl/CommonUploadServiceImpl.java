package com.self.datatransferserver.modules.test.impl;

import com.self.datatransferserver.common.ApiResult;
import com.self.datatransferserver.config.CustomProperties;
import com.self.datatransferserver.constant.Constant;
import com.self.datatransferserver.enums.ResponseStatusEnum;
import com.self.datatransferserver.enums.ServiceType;
import com.self.datatransferserver.modules.base.BaseReceiveService;
import com.self.datatransferserver.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 通用上传文件处理
 */
@Slf4j
@Service("commonUploadServiceImpl")
public class CommonUploadServiceImpl extends BaseReceiveService {

    @Autowired
    private CustomProperties customProperties;

    @Override
    protected void init(ServiceType serviceType, HttpServletResponse response) {
        //初始化临时接收目录
        File tempFile = new File(customProperties.getDirRoot() + customProperties.getDirTemp());
        if(!tempFile.exists()){
            FileUtil.mkdirs(tempFile.toPath());
        }

        //文件保存成功目录
        File saveSuccessFile = new File(customProperties.getDirRoot() + customProperties.getDirSaveSuccess());
        if(!saveSuccessFile.exists()){
            FileUtil.mkdirs(saveSuccessFile.toPath());
        }
    }

    @Override
    protected ApiResult validate(String sender, String fileName, ServiceType serviceType, MultipartFile multipartFile, HttpServletResponse response) {
        //校验上传文件格式是否正确
        if(!fileName.endsWith(Constant.FILE_JSON_SUFFIX)){
            return ApiResult.result(ResponseStatusEnum.SYS_TYPE_FILE_ERROR);
        }

        return null;
    }

    @Override
    protected ApiResult saveData(String sender, String fileName, ServiceType serviceType, MultipartFile multipartFile, HttpServletResponse response) {
        //上传接收文件
        File receiveFile = null;

        try {
            //接收文件到临时目录
            multipartFile.transferTo(Paths.get(customProperties.getDirRoot() + customProperties.getDirTemp() + "/" + fileName));
            receiveFile = Paths.get(customProperties.getDirRoot() + customProperties.getDirTemp() + "/" + fileName).toFile();

            if(!receiveFile.exists()){
                return ApiResult.result(ResponseStatusEnum.SYS_REQUEST_ERROR);
            }

            //处理文件--TODO

            //移动文件到保存成功目录
            Files.move(receiveFile.toPath(), Paths.get(customProperties.getDirRoot() + customProperties.getDirSaveSuccess(), fileName));
        }catch (Exception e){
            log.error("文件：{} 上传处理异常", fileName);
            return ApiResult.result(ResponseStatusEnum.SYS_REQUEST_ERROR);
        }finally {
            //删除临时接收文件
            if(receiveFile != null && receiveFile.exists()){
                receiveFile.delete();
            }
        }

        return ApiResult.success();
    }

}
