package com.youtube_demo.controller;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youtube_demo.service.SearchService;
import com.youtube_demo.util.commonMethod.JsonHandling;
import com.youtube_demo.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/5 18:19
 * @description 搜索相关实现
 */
@RestController
@Log4j
public class SearchController {
    @Autowired
    SearchService searchService;

    /**
     * @description: 根据键入的查找内容查找对应的youtube信息，并返回(默认返回maxresult=50)
     * @author: wyk
     * @date: 2022/8/1 17:50
     * @param: [request]
     * @return: Json
     **/
    @PostMapping("search")
    public Result<HashMap<String, Object>> search(@RequestBody String params){
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String q = (String) jsonObject.get("inputText");
        String key = (String) jsonObject.get("key");
        String pageToken = (String) jsonObject.get("nextPageToken");

        ArrayList<HashMap<String, String>> resList = new ArrayList<HashMap<String, String>>();
        HashMap<String, Object> res = new HashMap<>();

        String searchListStr = searchService.getSearchList(q, "video", 50, "", key, pageToken);
        JSONObject resJson = JSONUtil.parseObj(searchListStr);
        pageToken = (String) resJson.get("nextPageToken");
        getIdAndText(searchListStr, resList);
        res.put("nextPageToken", pageToken);
        res.put("items", resList);

        return Result.success(res);
    }

    /**
     * @description: 根据视频数量获取
     * @author: wyk
     * @date: 2022/8/23 10:23
     * @param: [params]
     * @return: com.youtube_demo.util.result.Result<java.util.ArrayList<java.util.HashMap<java.lang.String,java.lang.String>>>
     **/
    @PostMapping("getVideoIds")
    public Result<ArrayList<HashMap<String, String>>> getVideoIds(@RequestBody String params){
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String q = (String) jsonObject.get("q");
        String videoCountStr = (String) jsonObject.get("videoCount");
        int videoCount = Integer.parseInt(videoCountStr);
        String key = (String) jsonObject.get("key");

        //分页查询
        String nextPageToken = "";

        ArrayList<HashMap<String, String>> resList = new ArrayList<HashMap<String, String>>();

        String res = "";
        if(videoCount < 50){
            res = searchService.getSearchList(q, "video", videoCount, "", key, "");
            getIdAndText(res, resList);
        }
        else {
            int n = videoCount;
            do {
                if(n > 50){
                    res = searchService.getSearchList(q, "video", 50, "", key, nextPageToken);
                    n = n - 50;
                    JSONObject resJson = JSONUtil.parseObj(res);
                    nextPageToken = (String) resJson.get("nextPageToken");
                    System.out.println("nextPageToken => " + nextPageToken);
                    getIdAndText(res, resList);
                }else {
                    res = searchService.getSearchList(q, "video", n, "", key, nextPageToken);
                    n = 0;
                    getIdAndText(res, resList);
                }
            } while (n > 0);
        }
        log.debug("返回列表信息 => " + resList);
        return Result.success(resList);
    }


    public void getIdAndText(String res, ArrayList<HashMap<String, String>> resList){
        JSONObject resJson = JSONUtil.parseObj(res);
        JSONArray resJsonArray = JSONUtil.parseArray(resJson.get("items"));
        for (Object o : resJsonArray) {
            //每个视频详细信息
            JSONObject jsonObject1 = (JSONObject) o;
            HashMap<String, String> map = new HashMap<>();
            map.put("id", (String) JSONUtil.parseObj(jsonObject1.get("id")).get("videoId"));
            map.put("text", (String) JSONUtil.parseObj(jsonObject1.get("snippet")).get("title"));
            resList.add(map);
        }
    }

}
