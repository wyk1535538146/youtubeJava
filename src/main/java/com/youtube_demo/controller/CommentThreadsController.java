package com.youtube_demo.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youtube_demo.service.CommentThreadsService;
import com.youtube_demo.util.commonMethod.JsonHandling;
import com.youtube_demo.util.result.Result;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/8 9:01
 * @description YouTube CommentThreads接口
 */
@RestController
@Log4j
public class CommentThreadsController {
    @Autowired
    CommentThreadsService commentThreadsService;

    /**
     * @description: 获取视频的顶级评论
     * @author: wyk
     * @date: 2022/8/3 8:46
     * @param: nextPageToken, key, videoId,
     * @return: {"code": xxx, "message": "", data:{"nextPageToken": xxx, "items": ["id":"", "text":""]}}
     **/
    @PostMapping("CommentThreads_list")
    public Result<HashMap<String, Object>> CommentThreads_list(@RequestBody JSONObject params){
        String pageToken = (String) params.get("nextPageToken");
        String key = (String) params.get("key");
        String videoId = (String) params.get("videoId");
        String commentKey = (String) params.get("commentKey");

        ArrayList<HashMap<String, String>> resList = new ArrayList<>();

        String commentThreadsList = commentThreadsService.getCommentThreadsList(videoId, 100, pageToken, key, commentKey);

        //返回结果处理
        JSONObject resJson = JSONUtil.parseObj(commentThreadsList);
        pageToken = (String) resJson.get("nextPageToken");

        JSONArray resJsonArray = JSONUtil.parseArray(resJson.get("items"));
        for (Object o : resJsonArray) {
            //每个视频详细信息
            JSONObject jsonObject1 = (JSONObject) o;
            HashMap<String, String> map = new HashMap<>();
            map.put("id", (String) jsonObject1.get("id"));
            map.put("text", (String) jsonObject1.getJSONObject("snippet").getJSONObject("topLevelComment").getJSONObject("snippet").get("textOriginal"));
            resList.add(map);
        }
        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("nextPageToken", pageToken);
        resMap.put("items", resList);
        log.debug("CommentThreadsController:list" + resMap);
        return Result.success(resMap);
    }


    /**
     * @description: 在视频中创建顶级评论
     * @author: wyk
     * @date: 2022/8/3 17:53
     * @param: [request]
     * @return: java.lang.String
     **/
    @PostMapping("commentThreads_insert")
    public Result<Object> commentThreads_insert(@RequestBody String params){
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String textOriginal = (String) jsonObject.get("commentText");
        String videoId = (String) jsonObject.get("videoId");
        String key = (String) jsonObject.get("key");
        String token = (String) jsonObject.get("access_token");

        String res = commentThreadsService.commentThreads_insert(videoId, textOriginal, key, token);

        log.debug("commentThreads_insert res => " + res);

        return JsonHandling.handleResponseOfService(res);
    }
}
