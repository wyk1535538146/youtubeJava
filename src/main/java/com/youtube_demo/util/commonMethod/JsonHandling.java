package com.youtube_demo.util.commonMethod;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youtube_demo.entity.LiveChatMessage;
import com.youtube_demo.util.result.Result;

/**
 * @author wyk
 * @date 2022/8/5 19:23
 * @description json的一些相关处理
 */
public class JsonHandling {

    /**
     * @description: 处理YouTube官方返回结果
     * @author: wyk
     * @date: 2022/8/23 10:15
     * @param: [jsonStr]
     * @return: com.youtube_demo.util.result.Result<java.lang.Object>
     **/
    public static <T> Result<Object> handleResponseOfService(String jsonStr){
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        if (jsonObject.get("error") != null) {
            System.out.println(jsonObject);
            JSONObject errorJson = JSONUtil.parseObj(jsonObject.get("error"));
            return Result.fail((Integer) errorJson.get("code"), (String) errorJson.get("message"));
        } else {
            return Result.success(jsonObject);
        }
    }

}
