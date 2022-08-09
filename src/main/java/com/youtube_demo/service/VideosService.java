package com.youtube_demo.service;

import cn.hutool.http.HttpRequest;
import com.youtube_demo.util.constText.YouTubeConst;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/8 10:01
 * @description
 */
@Service
public class VideosService {

    /**
     * @description: YouTube Data API videos:getRating
     * @author: wyk
     * @date: 2022/8/8 10:13
     * @param: [id]
     * @return: java.lang.String
     **/
    public String getRating(String id){
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", YouTubeConst.KEY.getText());
        param.put("id", id);

        String token = Oauth.tokenString;

        String url = YouTubeConst.BASE_URL.getText() + "/videos/getRating";

        return HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .form(param)
                .header("Accept", "application/json")
                .execute().body();
    }

    /**
     * @description: YouTube Data API videos:rate
     * @author: wyk
     * @date: 2022/8/8 10:13
     * @param: [id, rating]
     * @return: java.lang.String
     **/
    public String rate(String id, String rating){
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", YouTubeConst.KEY.getText());
        param.put("id", id);
        param.put("rating", rating);

        String token = Oauth.tokenString;

        String url = YouTubeConst.BASE_URL.getText() + "/videos/rate";
        return HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .form(param)
                .execute().body();
    }
}
