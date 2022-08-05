package com.youtube_demo.entity;

import lombok.Data;

import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/5 9:58
 * @description
 */
@Data
public class LiveChatMessage {
    private String kind;

    private String etag;

    private String id;

    private HashMap<String, Object> snippet;
}
