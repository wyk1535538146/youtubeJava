package com.youtube_demo.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youtube_demo.service.CommentThreadsService;
import com.youtube_demo.util.commonMethod.JsonHandling;
import com.youtube_demo.util.result.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wyk
 * @date 2022/8/8 9:01
 * @description YouTube CommentThreads接口
 */
@RestController
public class CommentThreadsController {
    @Autowired
    CommentThreadsService commentThreadsService;

    private static final Logger logger = Logger.getLogger(CommentThreadsController.class);

    /**
     * @description: 获取视频的顶级评论
     * @author: wyk
     * @date: 2022/8/3 8:46
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("api_show")
    public String show(HttpServletRequest request){
        String videoId = request.getParameter("videoId");

        String commentThreadsList = commentThreadsService.getCommentThreadsList(videoId, 50);
        System.out.println(commentThreadsList);
        //todo res处理
        return commentThreadsList;
    }


    /**
     * @description: 在视频中创建顶级评论
     * @author: wyk
     * @date: 2022/8/3 17:53
     * @param: [request]
     * @return: java.lang.String
     **/
    @PostMapping("commentThreads_insert")
    public Result<Object> commentThreads_insert(@RequestBody String params){
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String textOriginal = (String) jsonObject.get("commentText");
        String videoId = (String) jsonObject.get("videoId");
        String key = (String) jsonObject.get("key");
        String token = (String) jsonObject.get("access_token");

        String res = commentThreadsService.commentThreads_insert(videoId, textOriginal, key, token);

        logger.debug("commentThreads_insert res => " + res);

        return JsonHandling.handleResponseOfService(res);
    }
}
