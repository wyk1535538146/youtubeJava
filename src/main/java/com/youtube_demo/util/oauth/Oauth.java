package com.youtube_demo.util.oauth;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.youtube_demo.entity.Token;
import com.youtube_demo.entity.User;
import com.youtube_demo.util.constText.YouTubeConst;
import com.youtube_demo.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private static final String client_id = "656863191434-f0ab0j31jrjtas919i2jrt1ejj1bcgum.apps.googleusercontent.com";
    private static final String client_secret = "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";

    //workspace 这个号
    /*private static final String client_id = "508785976421-cr17tf61kj47jdaefigaas2vsnqi9emd.apps.googleusercontent.com";
    private static final String client_secret = "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";*/
    public static String tokenString = "";
    public static User nowUser = null;
    public static String refresh_token = "";
    public static ArrayList<User> userList = new ArrayList<User>();

    @RequestMapping("getCode")
    //@ResponseBody
    public String getCode(HttpServletRequest request){
        String code = request.getParameter("code");
        System.out.println();
        //官方
        String url = "https://oauth2.googleapis.com/token";
//        String url = "http://192.168.2.140:7001/token";

        HashMap<String, Object> param = new HashMap<>();
        param.put("client_id", client_id);
        param.put("client_secret", client_secret);
        param.put("grant_type", "authorization_code");
        param.put("code", code);
        param.put("redirect_uri", "http://localhost:8081/getCode");
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


        return "redirect:http://localhost:8081/";
//        return "redirect:http://localhost:3000/";
    }

    @RequestMapping("revoke")
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
    }

    public Token refresh_token(Token token){
        String url = "https://oauth2.googleapis.com/token";
        HashMap<String, Object> param = new HashMap<>();
        param.put("client_id", client_id);
        param.put("client_secret", client_secret);
        param.put("grant_type", "authorization_code");
        param.put("refresh_token", token.getRefresh_token());


        String res = HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .header("content-type", "application/x-www-form-urlencoded")//头信息，多个头信息多次调用此方法即可
                .form(param)
                .execute().body();
        Token token_new = JSONUtil.toBean(res, Token.class);
        token_new.setRefresh_token(token.getRefresh_token());
        return token_new;
    }




    @RequestMapping("skipToOauth")
    public String skipToOauth(){

        String url = "https://oauth2.googleapis.com/token?grant_type=client_credentials&client_id=" + client_id + "&client_secret=" + client_secret;
        String res1 = HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .execute().body();
        /*ArrayList<String> scopes = new ArrayList<>();
        scopes.add("https://www.googleapis.com/auth/youtube.force-ssl");
        scopes.add("https://www.googleapis.com/auth/youtubepartner-channel-audit");
        scopes.add("https://www.googleapis.com/auth/youtubepartner");
        scopes.add("https://www.googleapis.com/auth/youtube");
        scopes.add("https://www.googleapis.com/auth/youtube.readonly");
        scopes.add("https://www.googleapis.com/auth/userinfo.profile");
        scopes.add("https://www.googleapis.com/auth/userinfo.email");
        GoogleCredential credential = GoogleCredential.*/
/*
        String baseUrl = "https://accounts.google.com/o/oauth2/auth";
        HashMap<String, Object> param = new HashMap<>();
        param.put("response_type", "code");
        param.put("client_id", client_id);
        param.put("redirect_uri", "http://localhost:8081/getCode");
        param.put("scope", "https://www.googleapis.com/auth/youtube.force-ssl https://www.googleapis.com/auth/youtubepartner-channel-audit https://www.googleapis.com/auth/youtubepartner https://www.googleapis.com/auth/youtube https://www.googleapis.com/auth/youtube.readonly https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email");
        param.put("access_type", "offline");  //是否可以刷新令牌
        param.put("prompt", "select_account"); //提示用户选择一个账户

        String res = HttpRequest.get(baseUrl)
                .setHttpProxy("127.0.0.1", 4780)
                .form(param)
                .execute().body();

        System.out.println(res);
*/
        System.out.println(res1);
        return "redirect:http://localhost:8081/";
    }

}
