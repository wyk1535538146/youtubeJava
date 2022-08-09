package com.youtube_demo.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.youtube_demo.service.CommentThreadsService;
import com.youtube_demo.util.constText.YouTubeConst;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/8 9:01
 * @description YouTube CommentThreads接口
 */
@RestController
public class CommentThreadsController {
    @Autowired
    CommentThreadsService commentThreadsService;

    /**
     * @description:
     * @author: wyk
     * @date: 2022/8/3 8:46
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("show")
    public String show(HttpServletRequest request){
        String videoId = request.getParameter("videoId");

        String commentThreadsList = commentThreadsService.getCommentThreadsList(videoId, 50);
        //todo res处理
        return commentThreadsList;
    }


    /**
     * @description:  在视频中创建顶级评论
     * @author: wyk
     * @date: 2022/8/3 17:53
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("commentThreads_insert")
    public String commentThreads_insert(HttpServletRequest request){
        String textOriginal = request.getParameter("commentText");
        String videoId = request.getParameter("videoId");

        String res = commentThreadsService.commentThreads_insert(videoId, textOriginal);
        //todo res处理
        return res;
    }
}
