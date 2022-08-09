package com.youtube_demo.controller;

import cn.hutool.http.HttpRequest;
import com.youtube_demo.service.VideosService;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/8 10:00
 * @description
 */
@RestController
public class VideosController {
    @Autowired
    VideosService videosService;

    //todo
    /**
     * @description: 获得你对该视频的rate(评价)
     * @author: wyk
     * @date: 2022/8/4 10:13
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("getRating")
    public String getRating(HttpServletRequest request){
        String id = request.getParameter("videoId");
        String res = videosService.getRating(id);
        //todo res处理
        return res;
    }

    /**
     * @description: 对视频的三个评分， 喜欢，不喜欢，不设置
     * @author: wyk
     * @date: 2022/8/3 19:36
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("videos_rate")
    public String videos_rate(HttpServletRequest request){
        String id = request.getParameter("id");
        String rating = request.getParameter("rating");

        String res = videosService.rate(id, rating);
        //todo res处理
        return res;
    }
}
