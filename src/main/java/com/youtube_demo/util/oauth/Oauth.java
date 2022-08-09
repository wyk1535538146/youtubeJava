package com.youtube_demo.util.oauth;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
        String url = "https://oauth2.googleapis.com/token";

        HashMap<String, Object> param = new HashMap<>();
        param.put("client_id", client_id);
        param.put("client_secret", client_secret);
        param.put("grant_type", "authorization_code");
        param.put("code", code);
        param.put("redirect_uri", "http://localhost:8080/getCode");

        String res = HttpRequest.post(url)
                .setHttpProxy("127.0.0.1", 4780)
                .form(param)
                .execute().body();
        Token token = JSONUtil.toBean(res, Token.class);

        //获取用户邮箱
        String getUserInfoUrl = "https://people.googleapis.com/v1/people/me?personFields=emailAddresses&key=" + YouTubeConst.KEY.getText();
        String res1 = HttpRequest.get(getUserInfoUrl)
                .setHttpProxy("127.0.0.1", 4780)
                .auth("Bearer " + token.getAccess_token())
                .execute().body();
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


        return "redirect:http://localhost:3000/";
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

        return "redirect:http://localhost:3000/";
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

}
