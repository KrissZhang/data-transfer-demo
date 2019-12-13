package com.self.datatransferserver.enums;

/**
 * 响应状态枚举
 */
public enum ResponseStatusEnum {

    /**
     * 响应码列表(自定义业务响应码应该从700开始)
     */
    SUCCESS(200, "操作成功"),
    MINISTRY_REUPLOAD(707, "重复上传"),
    SYS_API_ERROR(1007, "业务类型不存在"),
    SYS_REQUEST_ERROR(1010, "请求异常"),
    SYS_REQUEST_FAIL(1011, "请求失败");

    private Integer code;

    private String msg;

    ResponseStatusEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
