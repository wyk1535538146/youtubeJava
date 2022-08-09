package com.youtube_demo.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wyk
 * @date 2022/8/8 19:19
 * @description
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * @description: 读取缓存
     * @author: wyk
     * @date: 2022/8/8 19:20
     * @param: [key]
     * @return: java.lang.String
     **/
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @description: 写入缓存
     * @author: wyk
     * @date: 2022/8/8 19:21
     * @param: [key, value]
     * @return: boolean
     **/
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @description: 写入缓存,并设置过期时间
     * @author: wyk
     * @date: 2022/8/8 19:21
     * @param: [key, value, timeout(s), unit(TimeUnit.SECONDS)]
     * @return: boolean
     **/
    public boolean set(final String key, String value, long timeout, TimeUnit unit) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @description: 更新缓存
     * @author: wyk
     * @date: 2022/8/8 19:21
     * @param: [key, value]
     * @return: boolean
     **/
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @description: 删除缓存
     * @author: wyk
     * @date: 2022/8/8 19:21
     * @param: [key]
     * @return: boolean
     **/
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
