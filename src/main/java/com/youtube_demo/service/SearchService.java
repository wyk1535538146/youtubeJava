package com.youtube_demo.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.youtube_demo.util.constText.YouTubeConst;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/5 18:22
 * @description
 */
@Service
public class SearchService {
    String token = Oauth.tokenString;


    public String getSearchList(String q, String type, int maxResults, String eventType){
        // TODO 这部分参数感觉可以优化
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", YouTubeConst.KEY.getText());
        param.put("type", type);
        param.put("part", "snippet");
        param.put("q", q);
        param.put("maxResults", maxResults);
        //event有内容说明查找的是直播List
        if(!eventType.equals("")) param.put("eventType", eventType);

        String url = YouTubeConst.BASE_URL.getText() + "/search";

        String proxy = "127.0.0.1";  //代理ip
        int port = 4780;   //代理的端口，
        System.setProperty("proxyType", "4");
        System.setProperty("proxyPort", Integer.toString(port));
        System.setProperty("proxyHost", proxy);
        System.setProperty("proxySet", "true");

        System.out.println(url + "\n" + param.get("key"));

        /*String res = HttpUtil.get(url,param);

        return res;*/
        return HttpRequest.get(url)
                .setHttpProxy("127.0.0.1", 4780)
                .form(param)
                .execute().body();
    }

}
