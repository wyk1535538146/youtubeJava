package com.youtube_demo.entity;

import lombok.Data;

import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/4 19:27
 * @description
 */
@Data
public class Video {
    private String kind;

    private String etag;

    private String id;

    private String pageInfo;

    //视频的基本信息
    private HashMap<String, Object> snippet;

    //视频的统计信息
    private HashMap<String, Object> statistics;

    //直播信息
    private HashMap<String, Object> liveStreamingDetails;
}
