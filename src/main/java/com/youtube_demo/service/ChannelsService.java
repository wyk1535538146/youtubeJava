package com.youtube_demo.service;

import cn.hutool.http.HttpRequest;
import com.youtube_demo.util.constText.YouTubeConst;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/8 10:15
 * @description
 */
@Service
public class ChannelsService {

    /**
     * @description: YouTube Data API Channels:list使用， 该
     * @author: wyk
     * @date: 2022/8/8 11:11
     * @param: []
     * @return: java.lang.String
     **/
    public String getChannel(){
        String url = YouTubeConst.BASE_URL.getText() + "/channels?part=snippet,contentDetails,statistics&mine=true&key=" + YouTubeConst.KEY.getText();
        String token = Oauth.tokenString;
        return HttpRequest.get(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .execute().body();
    }

    public String editChannels(String id, String description){
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", YouTubeConst.KEY.getText());
        param.put("part", "brandingSettings");

        String body = "{'id':'" + id + "','brandingSettings':{'channel': {'description': '" + description + "', 'defaultLanguage': 'en'}}}";
        String url = YouTubeConst.BASE_URL.getText() + "/channels";

        System.out.println(body);
        String token = Oauth.tokenString;

        return HttpRequest.put(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .form(param)
                .body(body)
                .execute().body();
    }
}
