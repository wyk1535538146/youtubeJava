package com.youtube_demo.entity;

import lombok.Data;

/**
 * @author wyk
 * @date 2022/8/9 9:11
 * @description google 账号一些相关信息 待完善
 */
@Data
public class User {
    private String email;

    private Token token;
}
