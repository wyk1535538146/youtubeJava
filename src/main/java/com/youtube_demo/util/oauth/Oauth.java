package com.youtube_demo.util.oauth;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youtube_demo.entity.User;
import com.youtube_demo.util.commonMethod.JsonHandling;
import com.youtube_demo.util.redis.RedisUtils;
import com.youtube_demo.util.result.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/2 17:25
 * @description Oauth 2.0授权
 */
@Controller
public class Oauth {

    @Autowired
    RedisUtils redisUtils;

    private static final Logger logger = Logger.getLogger(Oauth.class);
    /*private static final String client_id = "656863191434-f0ab0j31jrjtas919i2jrt1ejj1bcgum.apps.googleusercontent.com";
    private static final String client_secret = "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";*/

    //workspace 这个号
    /*private static final String client_id = "508785976421-cr17tf61kj47jdaefigaas2vsnqi9emd.apps.googleusercontent.com";
    private static final String client_secret = "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";*/
    public static String tokenString = "";
    public static User nowUser = null;
    public static String refresh_token = "";
    public static ArrayList<User> userList = new ArrayList<User>();

    /*@RequestMapping("getCode")
//    @ResponseBody
    public String getCode(HttpServletRequest request){
        String code = request.getParameter("code");
        System.out.println(code);
        //官方
        String url = "https://oauth2.googleapis.com/token";
//        String url = "http://192.168.2.140:7001/token";

        HashMap<String, Object> param = new HashMap<>();
        param.put("client_id", client_id);
        param.put("client_secret", client_secret);
        param.put("grant_type", "authorization_code");
        param.put("code", code);
        param.put("redirect_uri", "http://localhost:8000/cloud/account/account");
        // todo 上面一个，下面一个，前端点击登录一个地方  静态页面一个
        String res = HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .form(param)
                .execute().body();
        Token token = JSONUtil.toBean(res, Token.class);
        System.out.println("token => " + token);
        //获取用户邮箱
        //官方
        String getUserInfoUrl = "https://people.googleapis.com/v1/people/me?personFields=emailAddresses&key=" + YouTubeConst.KEY.getText();
//        String getUserInfoUrl = "http://192.168.2.140:7001/people/me?personFields=emailAddresses&key=" + YouTubeConst.KEY.getText();
        String res1 = HttpRequest.get(getUserInfoUrl)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token.getAccess_token())
                .execute().body();
        System.out.println(res1);
        JSONObject jsonObject = (JSONObject) (JSONUtil.parseArray((JSONUtil.parseObj(res1)).get("emailAddresses"))).get(0);
        User user = new User();
        user.setToken(token);
        user.setEmail((String) jsonObject.get("value"));


        if(!userList.contains(user)){
            userList.add(user);
        }

        //遍历
        userList.forEach((user1 -> {
            System.out.println(user1.getEmail());
            System.out.println(user1.getToken());
        }));

        if(userList.isEmpty()){
            tokenString = "";
            nowUser = null;
        }
        else{
            nowUser = userList.get(0);
            tokenString = userList.get(0).getToken().getAccess_token();
        }


//        return token;
        return "redirect:http://localhost:8081/";
//        return "redirect:http://localhost:3000/";
    }*/

    @PostMapping("getCode")
    @ResponseBody
    public Result<Object> getCode(@RequestBody String params){
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String code = (String) jsonObject.get("code");
        String client_id = (String) jsonObject.get("client_id");
        String client_secret = (String) jsonObject.get("client_secret");

        System.out.println("code=> " + code + "\nclient_id=> " + client_id + "\nclient_secret=> " + client_secret);
        //官方
        String url = "https://oauth2.googleapis.com/token";

        HashMap<String, Object> param = new HashMap<>();
        param.put("client_id", client_id);
        param.put("client_secret", client_secret);
        param.put("grant_type", "authorization_code");
        param.put("code", code);
        param.put("redirect_uri", "http://localhost:8000/api/getCode");
        String res = HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .form(param)
                .execute().body();
        logger.debug(res);
        /*Token token = JSONUtil.toBean(res, Token.class);
        System.out.println("token => " + token);*/

        return JsonHandling.handleResponseOfService(res);
    }

    /*@RequestMapping("revoke")
    //@ResponseBody
    public String revoke(HttpServletRequest request){
        String url = "https://oauth2.googleapis.com/revoke";

        userList.forEach(user -> {
            HashMap<String, Object> param = new HashMap<>();
            String token = user.getToken().getAccess_token();
            System.out.println(token);
            param.put("token", token);

            String result2 = HttpRequest.post(url)
                    .setHttpProxy("127.0.0.1", 4780)
                    .header("content-type", "application/x-www-form-urlencoded")//头信息，多个头信息多次调用此方法即可
                    .form(param)//表单内容
                    .execute().body();
            System.out.println(result2);
        });
        userList.clear();
        nowUser = null;
        tokenString = "";

        return "redirect:http://localhost:8081/";

//        return "redirect:http://localhost:3000/";
    }*/
    @RequestMapping("revoke")
    //@ResponseBody
    public String revoke(HttpServletRequest request){
        String url = "https://oauth2.googleapis.com/revoke";
        String token = request.getParameter("token");
        HashMap<String, Object> param = new HashMap<>();
        param.put("token", token);

            String result2 = HttpRequest.post(url)
                    .setHttpProxy("127.0.0.1", 4780)
                    .header("content-type", "application/x-www-form-urlencoded")//头信息，多个头信息多次调用此方法即可
                    .form(param)//表单内容
                    .execute().body();
            System.out.println(result2);

        return "redirect:http://localhost:8081/";

//        return "redirect:http://localhost:3000/";
    }

    @PostMapping("refreshToken")
    @ResponseBody
    public Result<Object> refresh_token(@RequestBody String params){
        JSONObject jsonObject = JSONUtil.parseObj(params);
        String refresh_token = (String) jsonObject.get("refresh_token");
        String client_id = (String) jsonObject.get("client_id");
        String client_secret = (String) jsonObject.get("client_secret");

        String url = "https://oauth2.googleapis.com/token";
        HashMap<String, Object> param = new HashMap<>();
        param.put("client_id", client_id);
        param.put("client_secret", client_secret);
        param.put("grant_type", "refresh_token");
        param.put("refresh_token", refresh_token);


        String res = HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .header("content-type", "application/x-www-form-urlencoded")//头信息，多个头信息多次调用此方法即可
                .form(param)
                .execute().body();
        logger.debug(res);
        return JsonHandling.handleResponseOfService(res);
    }

}
