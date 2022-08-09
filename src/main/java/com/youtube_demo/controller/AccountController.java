package com.youtube_demo.controller;

import com.youtube_demo.entity.User;
import com.youtube_demo.util.oauth.Oauth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;

/**
 * @author wyk
 * @date 2022/8/9 11:13
 * @description
 */
@RestController
public class AccountController {

    /*@RequestMapping("getUsersEmail")
    public ArrayList<String> getUsersEmail(){
        ArrayList<String> list = new ArrayList<>();
        Oauth.userList.forEach(user -> {
            list.add(user.getEmail());
            System.out.println(user.getEmail());
        });

        return list;
    }*/

    /**
     * @description: 获取google 账号 邮箱地址
     * @author: wyk
     * @date: 2022/8/9 15:34
     * @param: []
     * @return: java.util.ArrayList<java.util.HashMap<java.lang.String,java.lang.Object>>
     **/
    @RequestMapping("getUsersEmail")
    public ArrayList<HashMap<String, Object>> getUsersEmail(){
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        Oauth.userList.forEach(user -> {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("label", user.getEmail());
            hashMap.put("key", user.getEmail());
            list.add(hashMap);
            System.out.println(user.getEmail());
        });
        return list;
    }


    //todo 完善写法
    /**
     * @description:
     * @author: wyk
     * @date: 2022/8/9 14:20
     * @param: [request]
     * @return: java.lang.String
     **/
    @RequestMapping("changeAccount")
    public String changeAccount(HttpServletRequest request){
        String email = request.getParameter("email");
        for(int i = 0; i < Oauth.userList.size(); i++){
            User user = Oauth.userList.get(i);
            if(user.getEmail().equals(email)){
                Oauth.nowUser = user;
                Oauth.tokenString = user.getToken().getAccess_token();
                System.out.println(Oauth.nowUser + "   " + Oauth.tokenString);
                return "true";
            }
        }
        return "false";
    }
}
