package com.youtube_demo.entity;

import lombok.Data;

import java.util.Map;

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
    //视频的基本信息
    private Map<String, String> snippet;
    //视频的统计信息
    private Map<String, String> statistics;
    //直播信息
    private Map<String, String> liveStreamingDetails;
}
