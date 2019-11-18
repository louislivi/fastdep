package com.louislivi.fastdep.redis;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author : louislivi
 */
@Configuration
@AutoConfigureBefore({RedisAutoConfiguration.class})
@Import({FastDepRedisRegister.class, FastDepRestTemplateConfig.class})
public class FastDepRedisAutoConfiguration {
}
