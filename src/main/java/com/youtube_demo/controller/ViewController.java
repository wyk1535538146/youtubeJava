package com.youtube_demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wyk
 * @date 2022/8/10 10:22
 * @description
 */
@Controller
public class ViewController implements ErrorController {
    /*@RequestMapping(value="/**")
    public String index(){
        return "index";
    }*/
}
