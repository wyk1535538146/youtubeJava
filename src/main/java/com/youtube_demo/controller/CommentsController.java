package com.youtube_demo.controller;

import cn.hutool.json.JSONObject;
import com.youtube_demo.service.CommentsService;
import com.youtube_demo.util.commonMethod.JsonHandling;
import com.youtube_demo.util.result.Result;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wyk
 * @date 2022/8/8 9:42
 * @description comments 相关Controller
 */
@RestController
@Log4j
public class CommentsController {
    @Autowired
    CommentsService commentsService;

    /**
     * @description: 在别人的评论下回复，api中提到只能在顶级评论中回复
     * @author: wyk
     * @date: 2022/8/3 19:23
     * @param: addData, topicLevelCommentId, key, access_token
     * @return: code message data(res)
     **/
    @PostMapping("comments_insert")
    public Result<Object> comments_insert(@RequestBody JSONObject params){
        String textOriginal = (String) params.get("addData");
        String parentId = (String) params.get("topicLevelCommentId");   //顶级评论的id
        String key = (String) params.get("key");
        String token = (String) params.get("access_token");

        String res = commentsService.comments_insert(parentId, textOriginal, key, token);
        log.debug("res => " + res);
        return JsonHandling.handleResponseOfService(res);
    }

    /**
     * @description: 获取顶级评论的子评论（回复）
     * @author: wyk
     * @date: 2022/8/3 17:53
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("getChildComments")
    public String getChildComments(HttpServletRequest request){
        String parentId = request.getParameter("parentId");
        String res = commentsService.getChildComments(parentId, 50);
        //todo res处理
        return res;
    }
}
