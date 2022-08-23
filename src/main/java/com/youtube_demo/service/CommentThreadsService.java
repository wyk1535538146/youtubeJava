package com.youtube_demo.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.youtube_demo.util.constText.YouTubeConst;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/8 9:05
 * @description 对于YouTube Data API 的调用
 */
@Service
public class CommentThreadsService {

    /**
     * @description: 获取该视频的顶级评论
     * @author: wyk
     * @date: 2022/8/12 12:21
     * @param: [videoId, maxResults]
     * @return: java.lang.String
     **/
    public String getCommentThreadsList(String videoId, int maxResults){
        HashMap<String, Object> param = new HashMap<>();
        param.put("key", YouTubeConst.KEY.getText());
        param.put("part", "snippet");
        param.put("videoId", videoId);
        param.put("maxResults", maxResults);
        param.put("order","relevance");

        String url = YouTubeConst.BASE_URL.getText() + "/commentThreads";
        System.out.println(url);
//        String res = HttpUtil.get(url,param);
        return HttpRequest.get(url)
                .setHttpProxy("127.0.0.1", 4780)
                .form(param)
                .execute().body();
    }


    /**
     * @description: commentThreads:insert 评论视频（插入顶级评论）
     * @author: wyk
     * @date: 2022/8/12 12:20
     * @param: [videoId, textOriginal]
     * @return: java.lang.String
     **/
    public String commentThreads_insert(String videoId, String textOriginal, String key, String token){
        String snippetString = "{\"snippet\":{\"videoId\":\"" + videoId + "\",\"topLevelComment\":{\"snippet\":{\"textOriginal\":\"" + textOriginal + "\"}}}}";
        String url = YouTubeConst.BASE_URL.getText() + "/commentThreads?part=snippet&key=" + key;

        return HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token)
                .body(snippetString)
                .execute().body();
    }
}
