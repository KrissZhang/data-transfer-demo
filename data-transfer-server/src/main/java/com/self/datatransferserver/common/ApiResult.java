package com.self.datatransferserver.common;

import com.self.datatransferserver.enums.ResponseStatusEnum;
import lombok.Data;

/**
 * 响应结果
 * @param <T>
 */
@Data
public class ApiResult<T> {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public ApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 响应成功
     * @return 操作成功
     */
    public static ApiResult<String> success(){
        return new ApiResult<>(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMsg(), null);
    }

    /**
     * 响应成功
     * @return 操作成功
     */
    public static <E> ApiResult<E> success(E e){
        return new ApiResult<>(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMsg(), e);
    }

    /**
     * 请求失败
     * @param msg 失败信息
     * @return 请求失败
     */
    public static ApiResult<String> fail(String msg){
        return new ApiResult<>(ResponseStatusEnum.SYS_REQUEST_FAIL.getCode(), msg, null);
    }

    /**
     * 返回响应
     * @param code 响应码
     * @param msg 响应信息
     * @param e 返回数据
     * @param <E>
     * @return 返回响应
     */
    public static <E> ApiResult<E> result(Integer code, String msg, E e){
        return new ApiResult<>(code, msg, e);
    }

    /**
     * 返回响应
     * @param responseStatusEnum 响应状态
     * @param <E>
     * @return 返回响应
     */
    public static <E> ApiResult<E> result(ResponseStatusEnum responseStatusEnum){
        return new ApiResult<>(responseStatusEnum.getCode(), responseStatusEnum.getMsg(), null);
    }

    /**
     * 返回响应
     * @param responseStatusEnum 响应状态
     * @param e 返回数据
     * @param <E>
     * @return 返回响应
     */
    public static <E> ApiResult<E> result(ResponseStatusEnum responseStatusEnum, E e){
        return new ApiResult<>(responseStatusEnum.getCode(), responseStatusEnum.getMsg(), e);
    }

    /**
     * 返回响应
     * @param responseStatusEnum 响应状态
     * @param msg 响应信息
     * @param e 返回数据
     * @param <E>
     * @return 返回响应
     */
    public static <E> ApiResult<E> result(ResponseStatusEnum responseStatusEnum, String msg, E e){
        return new ApiResult<>(responseStatusEnum.getCode(), msg, e);
    }

}
