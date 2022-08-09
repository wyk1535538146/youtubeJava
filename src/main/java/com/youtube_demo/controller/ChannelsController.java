package com.youtube_demo.controller;

import cn.hutool.http.HttpRequest;
import com.youtube_demo.service.ChannelsService;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/8 10:15
 * @description
 */
@RestController
public class ChannelsController {
    @Autowired
    ChannelsService channelsService;

    /**
     * @description: 获取用户频道信息
     * @author: wyk
     * @date: 2022/8/8 11:09
     * @param: []
     * @return: java.lang.String
     **/
    @RequestMapping("getChannel")
    public String getChannel(){
        String res = channelsService.getChannel();
        //todo res
        return res;
    }


    /**
     * @description: 修改个人频道信息   仅修改频道描述?
     * @author: wyk
     * @date: 2022/8/3 19:14
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("editChannels")
    public String editChannels(HttpServletRequest request){
        String description = request.getParameter("description");
        String id = request.getParameter("id");
        String res = channelsService.editChannels(id, description);
        //todo res
        return res;
    }
}
