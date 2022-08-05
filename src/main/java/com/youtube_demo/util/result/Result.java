package com.youtube_demo.util.result;

import lombok.Data;

/**
 * @author wyk
 * @date 2022/8/5 11:03
 * @description 返回结果类
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ReturnCode.SUCCESS.getCode());
        result.setMessage(ReturnCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(int code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

}
