package com.louislivi.fastdep.redis;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * FastDepRedisAutoConfiguration
 *
 * @author : louislivi
 */
@Configuration
@EnableConfigurationProperties({FastDepRedisProperties.class})
@AutoConfigureBefore({RedisAutoConfiguration.class})
@Import(FastDepRedisRegister.class)
public class FastDepRedisAutoConfiguration {
}
