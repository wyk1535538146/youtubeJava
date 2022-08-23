package com.youtube_demo.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.youtube_demo.util.constText.YouTubeConst;
import com.youtube_demo.util.oauth.Oauth;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/8 9:43
 * @description YouTube Data API comments内容接口service
 */
@Service
@Log4j
public class CommentsService {

    /**
     * @description: YouTube Data API Comment:insert 接口调用   参数并未完善，基本使用
     * @author: wyk
     * @date: 2022/8/8 9:57
     * @param: [parentId, textOriginal]
     * @return: java.lang.String
     **/
    public String comments_insert(String parentId, String textOriginal, String key, String token){
        //todo json格式
        String body = "{\"snippet\":{\"parentId\":\"" + parentId + "\",\"textOriginal\":\"" + textOriginal + "\"}}";

        String url = YouTubeConst.BASE_URL.getText() + "/comments?part=snippet&key=" + key;
        log.debug("requestBody => " + body);
        return HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .body(body)
                .execute().body();
    }

    /**
     * @description: YouTube Data API comment:list接口调用  参数并未完善，基本使用
     * @author: wyk
     * @date: 2022/8/8 9:58
     * @param: [parentId, maxResults]
     * @return: java.lang.String
     **/
    public String getChildComments(String parentId, int maxResults){
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", YouTubeConst.KEY.getText());
        param.put("part", "snippet");
        param.put("parentId", parentId);
        param.put("maxResults", maxResults);

        String url = YouTubeConst.BASE_URL.getText() + "/comments";

        return HttpRequest.get(url)
                .setHttpProxy("127.0.0.1", 4780)
                .form(param)
                .execute().body();
    }
}
