package com.youtube_demo.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.youtube_demo.controller.SearchController;
import com.youtube_demo.util.constText.YouTubeConst;
import com.youtube_demo.util.oauth.Oauth;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/5 18:22
 * @description
 */
@Service
@Log4j
public class SearchService {

    /**
     * @description: 根据相关内容，如查找关键字q，类型，最大返回数量等通过youtubeAPI获取查找结果
     * @author: wyk
     * @date: 2022/8/5 16:59
     * @param: [q, type, maxResults, eventType]
     * @return: java.lang.String
     **/
    public String getSearchList(String q, String type, int maxResults, String eventType){
        System.out.println(Oauth.tokenString);
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

        log.debug("Request Address => " + url + "\n" + param.get("key"));

        return HttpRequest.get(url)
                .setHttpProxy("127.0.0.1", 4780)
                .header("Accept", "application/json")
                .form(param)
                .execute().body();
    }


    public String getSearchList(String q, String type, int maxResults, String eventType, String key, String pageToken){
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", key);
        param.put("type", type);
        param.put("part", "snippet");
        param.put("q", q);
        param.put("maxResults", maxResults);
        //下一页参数，有携带就放到param中
        if(pageToken.equals("")) param.put("pageToken", pageToken);
        //event有内容说明查找的是直播List
        if(!eventType.equals("")) param.put("eventType", eventType);

        String url = YouTubeConst.BASE_URL.getText() + "/search";

        log.debug("Request Address => " + url + "\n" + param.get("key"));

        return HttpRequest.get(url)
                .setHttpProxy("127.0.0.1", 4780)
                .header("Accept", "application/json")
                .form(param)
                .execute().body();
    }

}
