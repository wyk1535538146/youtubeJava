package com.youtube_demo.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.youtube_demo.service.CommentsService;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/8 9:42
 * @description comments 相关Controller
 */
@RestController
public class CommentsController {
    @Autowired
    CommentsService commentsService;

    /**
     * @description: 在别人的评论下回复，api中提到只能在顶级评论中回复
     * @author: wyk
     * @date: 2022/8/3 19:23
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("comments_insert")
    public String comments_insert(HttpServletRequest request){
        String textOriginal = request.getParameter("commentText");
        String parentId = request.getParameter("parentId");   //顶级评论的id

        String res = commentsService.comments_insert(parentId, textOriginal);
        //todo res处理
        System.out.println(res);
        return res;
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
