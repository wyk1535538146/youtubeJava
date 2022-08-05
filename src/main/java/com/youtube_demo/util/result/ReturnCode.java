package com.youtube_demo.util.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wyk
 * @date 2022/8/5 11:42
 * @description 返回 代码_消息 常量类
 */

@Getter
@AllArgsConstructor
public enum ReturnCode {
    /**操作成功**/
    SUCCESS(200,"OK"),
    /**操作失败**/
    FORBIDDEN(403,"没有权限！"),
    /**未找到**/
    NOT_FOUND(404,"不存在相关内容！"),
    /**错误的请求**/
    REQUIRED(400,"错误的请求!"),
    /**服务器错误**/
    SERVER_ERROR(500, "服务器异常！");





    /**自定义状态码**/
    private final int code;
    /**自定义描述**/
    private final String message;

}
