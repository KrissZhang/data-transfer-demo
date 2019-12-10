package com.self.datatransferserver.enums;

/**
 * 业务分发配置枚举
 */
public enum ServiceType {

    //分发服务列表
    commonUploadServiceImpl("通用上传文件服务", "TRANSFER_TEST");

    //请求后缀
    private static final String _REQ = "_REQ";

    //响应后缀
    private static final String _RES = "_RES";

    //服务名称
    private String serviceName;

    //请求命令
    private String command;

    ServiceType(String serviceName, String command){
        this.serviceName = serviceName;
        this.command = command;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getReqCode(){
        return this.command + _REQ;
    }

    public String getResCode(){
        return this.command + _RES;
    }

}
