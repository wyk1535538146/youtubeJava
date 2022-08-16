package com.youtube_demo.controller;


import com.youtube_demo.service.SearchService;
import com.youtube_demo.util.commonMethod.JsonHandling;
import com.youtube_demo.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wyk
 * @date 2022/8/5 18:19
 * @description 搜索相关实现
 */
@RestController
public class SearchController {
    @Autowired
    SearchService searchService;

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
}
