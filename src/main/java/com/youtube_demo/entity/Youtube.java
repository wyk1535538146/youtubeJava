package com.youtube_demo.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author wyk
 * @date 2022/8/5 8:59
 * @description youtube常用返回结果
 */
@Data
public class Youtube<T> {
    private String kind;
    private String etag;
    private String pageInfo;
    private ArrayList<T> items;
}
