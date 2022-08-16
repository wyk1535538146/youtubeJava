package com.youtube_demo.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.youtube_demo.entity.Video;
import com.youtube_demo.util.constText.YouTubeConst;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/5 10:15
 * @description
 */
@Service
public class LiveChatService {



    /**
     * @description: 直播间发消息
     * @author: wyk
     * @date: 2022/8/5 10:57
     * @param: [liveChatId, messageText]
     * @return: com.youtube_demo.entity.LiveChatMessage
     **/
    public String liveChatMessage_insert(String id, String messageText){
        String token = Oauth.tokenString;
        String getUrl = YouTubeConst.BASE_URL.getText() + "/videos?part=liveStreamingDetails&id=" + id + "&key=" + YouTubeConst.KEY.getText();
        setProp();
        String res1 = HttpRequest.get(getUrl)
                .auth("Bearer " + token)
                .execute().body();
        JSONArray jsonArray = JSONUtil.parseArray(JSONUtil.parseObj(res1).get("items"));
        Video video = JSONUtil.toBean(JSONUtil.parseObj(jsonArray.get(0)), Video.class);
        String liveChatId = (String) video.getLiveStreamingDetails().get("activeLiveChatId");

        String url = YouTubeConst.BASE_URL.getText() + "/liveChat/messages?part=snippet&key=" + YouTubeConst.KEY.getText();
        HashMap<String, Object> snippetMap = new HashMap<>();
        snippetMap.put("liveChatId", liveChatId);
        snippetMap.put("type", "textMessageEvent");
        HashMap<String, Object> textMessageDetails = new HashMap<>();
        textMessageDetails.put("messageText", messageText);
        snippetMap.put("textMessageDetails", textMessageDetails);
        String snippet = JSONUtil.toJsonStr(JSONUtil.parseObj(snippetMap));
        String snippetStr = "{" + snippet + "}";


        //todo 转义
        snippetStr = "{\"snippet\":{\"liveChatId\":\"" + liveChatId + "\",\"type\":\"textMessageEvent\",\"textMessageDetails\":{\"messageText\":\"" + messageText + "\"}}}";

        System.out.println(snippet);
        System.out.println(url);

        System.out.println(token);
        return HttpRequest.post(url)
                .auth("Bearer " + token)
                .body(snippetStr)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .execute().body();
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
}
