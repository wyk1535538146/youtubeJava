package com.youtube_demo.entity;

import lombok.Data;

/**
 * @author wyk
 * @date 2022/8/2 18:24
 * @description google获取的token bean
 */
@Data
public class Token {
    private String access_token;
    private String expires_in;   //过期时间
    private String refresh_token;
    private String scope;
    private String token_type;
}
