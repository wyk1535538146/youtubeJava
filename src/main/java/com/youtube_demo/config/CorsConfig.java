package com.youtube_demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wyk
 * @date 2022/8/1 17:43
 * @description 解决跨域问题
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域请求的地址
        registry.addMapping("/**")
                // 允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 允许的请求方法
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                // 是否允许证书（cookies）
                .allowCredentials(true)
                // 跨域允许时间
                .maxAge(3600);
    }
}
