package com.louislivi.fastdep.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * FastDepRedis
 *
 * @author : louislivi
 */
@ConditionalOnProperty("fastdep.redis")
@ConfigurationProperties("fastdep")
public class FastDepRedis {
    private Map<String, RedisProperties> redis;

    public Map<String, RedisProperties> getRedis() {
        return redis;
    }

    public void setRedis(Map<String, RedisProperties> redis) {
        this.redis = redis;
    }
}
