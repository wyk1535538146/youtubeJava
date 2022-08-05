package com.youtube_demo.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.youtube_demo.util.oauth.Oauth;
import com.youtube_demo.service.LiveChatService;
import org.springframework.beans.factory.annotation.Autowired;
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
    //workspace 号
//    private static final String key = "AIzaSyDkAu2Vi-My8rxmvfVvXkt9Zuj7zuqAq2E";
    private static final String key = "AIzaSyADflK5MEC9uB8bvgLIQU01yuv6ur4Bn2c";
    private static final String baseUrl = "https://www.googleapis.com/youtube/v3";
//private static final String baseUrl = "http://192.168.2.140";
    @Autowired
    LiveChatService liveChatService;



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

        HashMap<String, Object> param = new HashMap<>();
        param.put("key", key);
        param.put("part", "snippet");
        param.put("videoId", videoId);
        param.put("maxResults", 50);
        param.put("order","relevance");

        String url = baseUrl + "/commentThreads";
        System.out.println(url);

        setProp();


        String res = HttpUtil.get(url,param);

        return res;
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
        String snippetString = "{'snippet':{'videoId':'" + videoId + "','topLevelComment':{'snippet':{'textOriginal':'" + textOriginal + "'}}}}";
        String url = baseUrl + "/commentThreads?part=snippet&key=" + key;
        String token = Oauth.tokenString;

        String res = HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .body(snippetString)
                .execute().body();
        return res;
    }

    //todo 未测试
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
        //todo json格式
        String body = "{'snippet':{'parentId':'" + parentId + "','textOriginal':'" + textOriginal + "'}}";


        HashMap<String, Object> param = new HashMap<>();
        param.put("key", key);
        param.put("part", "brandingSettings");

        String url = baseUrl + "/comments?part=snippet&key="+key;

        setProp();
        System.out.println(body);
        String token = Oauth.tokenString;
        String res = HttpRequest.post(url)
                .auth("Bearer " + token)
//                .form(param)//表单内容
                .body(body)
                .execute().body();
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
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", key);
        param.put("part", "snippet");
        param.put("parentId", parentId);
        param.put("maxResults", 50);

        String url = baseUrl + "/comments";

        setProp();

        String res = HttpUtil.get(url,param);

        return res;
    }

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
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", key);
        param.put("id", id);

        String token = Oauth.tokenString;

        String url = baseUrl + "/videos/getRating?id=" + id + "&key=" + key;

        String res = HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .header("Accept", "application/json")
                .execute().body();
        System.out.println(token + "\n" + url);
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
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", key);
        param.put("id", id);
        param.put("rating", rating);

        String token = Oauth.tokenString;

        String url = baseUrl + "/videos/rate";
        setProp();
        String res = HttpRequest.post(url)
                .auth("Bearer " + token)
                .form(param)
                .execute().body();


        return res;
    }



    @RequestMapping("getChannelId")
    public String getChannelId(){
        String url = baseUrl + "/channels?part=snippet,contentDetails,statistics&mine=true&key=" + key;
        String token = Oauth.tokenString;
        String res = HttpRequest.get(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .execute().body();
        System.out.println(res);
        return res;
    }


    /**
     * @description: 修改个人频道信息
     * @author: wyk
     * @date: 2022/8/3 19:14
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("editChannels")
    public String editChannels(HttpServletRequest request){
        String description = request.getParameter("description");
        String id = request.getParameter("id");
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", key);
        param.put("part", "brandingSettings");

        String body = "{'id':'" + id + "','brandingSettings':{'channel': {'description': '" + description + "', 'defaultLanguage': 'en'}}}";
        String url = baseUrl + "/channels?part=brandingSettings&key="+key;

        System.out.println(body);
        String token = Oauth.tokenString;


        String res = HttpRequest.put(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(body)
                .execute().body();
        System.out.println(res);
        return res;
    }



    //设置指定键对值的系统属性  这里主要是代理
    public void setProp(){
        String proxy = "127.0.0.1";  //代理ip
        int port = 4780;   //代理的端口，
        System.setProperty("proxyType", "4");
        System.setProperty("proxyPort", Integer.toString(port));
        System.setProperty("proxyHost", proxy);
        System.setProperty("proxySet", "true");
    }


    //todo 目前google获取到的数据全部没有改过结构，直接传到前端，到时候需要改一下，前端方便操作



    //todo 1.频道名字和简介 2.关键词搜索字数限制 3.直播间发消息
}
