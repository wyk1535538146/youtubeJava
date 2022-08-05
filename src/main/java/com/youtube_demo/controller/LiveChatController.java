package com.youtube_demo.controller;

import com.youtube_demo.service.LiveChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wyk
 * @date 2022/8/5 18:16
 * @description 和直播间聊天有关的操作
 */
@RestController
public class LiveChatController {
    @Autowired
    LiveChatService liveChatService;

    /**
     * @description: 直播间发消息
     * @author: wyk
     * @date: 2022/8/5 19:38
     * @param: [request]
     * @return: com.youtube_demo.util.result.Result<java.lang.Object>
     **/
    @RequestMapping("LiveChatMessages_insert")
    public String LiveChatMessages_insert(HttpServletRequest request) {
        String id = request.getParameter("videoId");
        String messageText = request.getParameter("messageText");

        String res = liveChatService.liveChatMessage_insert(id, messageText);

        System.out.println(res);
        return res;
//        return JsonHandling.handleResponseOfService(res);
    }
}
