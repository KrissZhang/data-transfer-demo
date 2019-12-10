package com.self.datatransferserver.service;

import com.self.datatransferserver.common.ApiResult;
import com.self.datatransferserver.enums.ServiceType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 接收文件服务
 */
public interface IBinFileReceiveService {

    /**
     * 接收文件服务
     * @param sender 发送者
     * @param fileName 文件名
     * @param serviceType 业务类别
     * @param multipartFile 上传文件
     * @param response 响应
     * @return 响应信息
     */
    ApiResult receiveData(String sender, String fileName, ServiceType serviceType, MultipartFile multipartFile, HttpServletResponse response);

}
