package com.self.datatransferserver.controller;

import com.self.datatransferserver.common.ApiResult;
import com.self.datatransferserver.enums.ResponseStatusEnum;
import com.self.datatransferserver.enums.ServiceType;
import com.self.datatransferserver.service.IBinFileReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 请求入口
 */
@Slf4j
@RestController
@RequestMapping("/binFile")
public class BinFileController {

    private Map<String, IBinFileReceiveService> receiveServiceMap;

    @Autowired
    public BinFileController(Map<String, IBinFileReceiveService> receiveServiceMap){
        this.receiveServiceMap = receiveServiceMap;
    }

    @PostMapping("/receive/{sender}/{fileName}")
    public ApiResult uploadDataFile(MultipartFile binFile, String sign, @PathVariable String sender, @PathVariable String fileName, HttpServletRequest request, HttpServletResponse response){
        try{
            //请求校验
            //String requestParam = request.getHeader("paramName");

            //公共参数校验

            //替换特殊字符
            fileName = fileName.replace("@_@", ".");

            //分发业务
            String[] fileNameArr = fileName.split("_");
            String command = fileNameArr[0] + "_" + fileNameArr[1];

            ServiceType serviceType = getServiceTypeByCommand(command);
            if(serviceType == null){
                return ApiResult.result(ResponseStatusEnum.SYS_API_ERROR, "参数错误，未找到指定的命令",null);
            }

            String serviceName = serviceType.name();
            if(receiveServiceMap.containsKey(serviceName)){
                return receiveServiceMap.get(serviceName).receiveData(sender, fileName, serviceType, binFile, response);
            }else{
                return ApiResult.result(ResponseStatusEnum.SYS_API_ERROR, "参数错误，未找到指定的服务", null);
            }
        }catch(Exception e){
            log.error("binFile请求异常");
            return ApiResult.result(ResponseStatusEnum.SYS_API_ERROR, e.getMessage(), null);
        }

    }

    /**
     * 通过命令获取业务类型
     * @param command 命令
     * @return 业务类型
     */
    private ServiceType getServiceTypeByCommand(String command){
        ServiceType[] serviceTypes = ServiceType.values();
        ServiceType serviceType = null;

        for (ServiceType type : serviceTypes){
            if((command != null) && (command.equals(type.getCommand()) || command.equals(type.name()))){
                serviceType = type;
                break;
            }
        }

        return serviceType;
    }

}
