package com.youtube_demo.controller;

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
public class SearchController {
    @Autowired
    SearchService searchService;

    private static final Logger logger = Logger.getLogger(SearchController.class);

    /**
     * @description: 根据键入的查找内容查找对应的youtube信息，并返回
     * @author: wyk
     * @date: 2022/8/1 17:50
     * @param: [request]
     * @return: Json
     **/
    @RequestMapping("search")
    public Result<Object> search(HttpServletRequest request){
        String inputText = request.getParameter("inputText");
        System.out.println(inputText);

        String searchListStr = searchService.getSearchList(inputText, "video", 50, "");

        return JsonHandling.handleResponseOfService(searchListStr);
    }

    @PostMapping("getVideoIds")
    public Result<ArrayList<HashMap<String, String>>> getVideoIds(@RequestBody String params){
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String q = (String) jsonObject.get("q");
        String videoCountStr = (String) jsonObject.get("videoCount");
        int videoCount = Integer.parseInt(videoCountStr);
        String key = (String) jsonObject.get("key");

        //分页查询
        String nextPageToken = "";
        String prevPageToken = "";

        ArrayList<HashMap<String, String>> resList = new ArrayList<HashMap<String, String>>();

        String res = "";
        if(videoCount < 50){
            res = searchService.getSearchList(q, "video", videoCount, "", key, "", "");
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
        else {
            int n = videoCount;
            do {
                if(n > 50){
                    res = searchService.getSearchList(q, "video", 50, "", key, nextPageToken, "");
                    n = n - 50;
                    JSONObject resJson = JSONUtil.parseObj(res);
                    nextPageToken = (String) resJson.get("nextPageToken");
                    System.out.println("nextPageToken => " + nextPageToken);
                    JSONArray resJsonArray = JSONUtil.parseArray(resJson.get("items"));
                    for (Object o : resJsonArray) {
                        //每个视频详细信息
                        JSONObject jsonObject1 = (JSONObject) o;
                        HashMap<String, String> map = new HashMap<>();
                        map.put("id", (String) JSONUtil.parseObj(jsonObject1.get("id")).get("videoId"));
                        map.put("text", (String) JSONUtil.parseObj(jsonObject1.get("snippet")).get("title"));
                        resList.add(map);
                    }
                }else {
                    res = searchService.getSearchList(q, "video", n, "", key, nextPageToken, "");
                    n = 0;
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
            } while (n > 0);
        }
        logger.debug("返回列表信息 => " + resList);
        return Result.success(resList);
    }

}
