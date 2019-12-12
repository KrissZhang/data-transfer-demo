package com.self.datatransferserver.modules.base;

import com.self.datatransferserver.common.ApiResult;
import com.self.datatransferserver.enums.ResponseStatusEnum;
import com.self.datatransferserver.enums.ServiceType;
import com.self.datatransferserver.service.IBinFileReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 接收文件服务基类
 */
@Slf4j
public abstract class BaseReceiveService implements IBinFileReceiveService {

    private String serviceName = "BaseReceiveService";

    @Override
    public ApiResult receiveData(String sender, String fileName, ServiceType serviceType, MultipartFile multipartFile, HttpServletResponse response) {
        try {
            ApiResult re;

            //检查和初始化目录、初始化变量等
            init(serviceType, response);

            //业务校验
            re = validate(sender, fileName, serviceType, multipartFile, response);
            if(re != null){
                return re;
            }

            //保存数据
            saveData(sender, fileName, serviceType, multipartFile, response);

            return re;
        } catch (Exception e) {
            log.error("[{}]请求异常", serviceName);
            return ApiResult.result(ResponseStatusEnum.SYS_REQUEST_ERROR, "请求异常：" + e.getMessage(), null);
        }
    }

    /**
     * 检查和初始化目录、初始化变量等
     */
    protected abstract void init(ServiceType serviceType, HttpServletResponse response);

    /**
     * 业务相关校验
     * @return 无异常返回null，有异常返回响应对象
     */
    protected abstract ApiResult validate(String sender, String fileName, ServiceType serviceType, MultipartFile multipartFile, HttpServletResponse response);

    /**
     * 接收存储数据
     * @return 接口响应结果
     */
    protected abstract ApiResult saveData(String sender, String fileName, ServiceType serviceType, MultipartFile multipartFile, HttpServletResponse response);

}
