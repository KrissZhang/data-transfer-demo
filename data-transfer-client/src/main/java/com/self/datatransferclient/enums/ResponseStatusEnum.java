package com.self.datatransferclient.enums;

/**
 * 响应状态枚举
 */
public enum ResponseStatusEnum {

    /**
     * 响应码列表(自定义业务响应码应该从700开始)
     */
    SUCCESS(200, "操作成功"),
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
