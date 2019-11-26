package com.louislivi.fastdep.test.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * RedisUtil
 *
 * @author : louislivi
 */
@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate redis1StringRedisTemplate;

    @Autowired
    private StringRedisTemplate redis2StringRedisTemplate;

    @Autowired
    private RedisTemplate redis2RedisTemplate;

    @Autowired
    private RedisTemplate redis1RedisTemplate;

    public RedisTemplate redisTemplate(String name) {
        RedisTemplate redisTemplate;
        switch (name) {
            case "redis2":
                redisTemplate = redis2RedisTemplate;
                break;
            default:
                redisTemplate = redis1RedisTemplate;
                break;
        }
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }

    public StringRedisTemplate stringRedisTemplate(String name) {
        StringRedisTemplate stringRedisTemplate;
        switch (name) {
            case "redis2":
                stringRedisTemplate = redis2StringRedisTemplate;
                break;
            default:
                stringRedisTemplate = redis1StringRedisTemplate;
                break;
        }
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }
}
