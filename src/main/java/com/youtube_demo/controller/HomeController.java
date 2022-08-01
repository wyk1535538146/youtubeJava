package com.youtube_demo.controller;

import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;



/**
 * @author wyk
 * @date 2022/8/1 15:17
 * @description
 */
@RestController
public class HomeController {
    private static final String key = "AIzaSyDkAu2Vi-My8rxmvfVvXkt9Zuj7zuqAq2E";
    private static final String baseUrl = "https://www.googleapis.com/youtube/v3";

    /**
     * @description: 根据键入的查找内容查找对应的youtube信息，并返回
     * @author: wyk
     * @date: 2022/8/1 17:50
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("search")
    public String search(HttpServletRequest request){
        String inputText = request.getParameter("inputText");

        HashMap<String, Object> param = new HashMap<>();
        param.put("key", key);
        param.put("type", "video");
        param.put("part", "snippet");
        param.put("q", inputText);
        param.put("maxResults", 10);

        String url = baseUrl + "/search";
        System.out.println(url);


        String proxy = "127.0.0.1";  //代理ip
        int port = 4780;   //代理的端口，
        System.setProperty("proxyType", "4");
        System.setProperty("proxyPort", Integer.toString(port));
        System.setProperty("proxyHost", proxy);
        System.setProperty("proxySet", "true");

        String res = HttpUtil.get(url,param);
        return res;


    }
}
