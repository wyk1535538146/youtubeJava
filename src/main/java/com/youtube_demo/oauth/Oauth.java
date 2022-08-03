package com.youtube_demo.oauth;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.youtube_demo.entity.Token;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author wyk
 * @date 2022/8/2 17:25
 * @description Oauth 2.0授权
 */
@Controller
public class Oauth {

    private static final String client_id = "508785976421-cr17tf61kj47jdaefigaas2vsnqi9emd.apps.googleusercontent.com";
    private static final String client_secret = "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";
    public static String tokenString = "";

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

        System.out.println(code);

        String proxy = "127.0.0.1";  //代理ip
        int port = 4780;   //代理的端口，
        System.setProperty("proxyType", "4");
        System.setProperty("proxyPort", Integer.toString(port));
        System.setProperty("proxyHost", proxy);
        System.setProperty("proxySet", "true");


        String res = HttpUtil.post(url,param);
        //JSONObject jsonObject = JSON.parseObject(res);
        Token token = JSON.parseObject(res, Token.class);
        System.out.println(token.getAccess_token());
        tokenString = token.getAccess_token();
        /*HttpSession session = request.getSession();
        session.setAttribute("token", token.getAccess_token());*/
        /*System.out.println(token.getAccess_token() + "\n" + session.getAttribute("token"));*/

        return "redirect:http://localhost:3000/";
    }

    @RequestMapping("revoke")
    //@ResponseBody
    public String revoke(HttpServletRequest request){
        String url = "https://oauth2.googleapis.com/revoke";

        HashMap<String, Object> param = new HashMap<>();
        String token = tokenString;
        System.out.println(token);

        param.put("token", token);


        String proxy = "127.0.0.1";  //代理ip
        int port = 4780;   //代理的端口，
        System.setProperty("proxyType", "4");
        System.setProperty("proxyPort", Integer.toString(port));
        System.setProperty("proxyHost", proxy);
        System.setProperty("proxySet", "true");

        String result2 = HttpRequest.post(url)
                .header("content-type", "application/x-www-form-urlencoded")//头信息，多个头信息多次调用此方法即可
                .form(param)//表单内容
                .execute().body();
        System.out.println(result2);

        return "redirect:http://localhost:3000/";
    }

}
