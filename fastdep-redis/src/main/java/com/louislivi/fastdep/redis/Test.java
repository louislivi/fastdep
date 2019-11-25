package com.louislivi.fastdep.redis;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Map;

/**
 * @author : louislivi
 */
public class Test implements EnvironmentAware {
    private Environment env;

    private Binder binder;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        Map map = binder.bind("fastdep.redis.redis1", Map.class).get();
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName((String) map.get("host"));
        configuration.setPort((Integer) map.get("port"));
        configuration.setDatabase((Integer) map.get("database"));
        String password = (String) map.get("password");
        if (!StringUtils.isEmpty(password)) {
            RedisPassword redisPassword = RedisPassword.of(password);
            configuration.setPassword(redisPassword);
        }
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        Map<String, Integer> pool = binder.bind("fastdep.redis.redis1.lettuce.pool", Map.class).get();
        genericObjectPoolConfig.setMaxIdle(pool.get("maxIdle"));
        Duration shutdownTimeout = binder.bind("fastdep.redis.redis1.shutdown-timeout", Duration.class).get();
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        if (shutdownTimeout != null) {
            builder.shutdownTimeout(shutdownTimeout);
        }
        LettuceClientConfiguration clientConfiguration = builder.poolConfig(genericObjectPoolConfig).build();
        return new LettuceConnectionFactory(configuration, clientConfiguration);
    }


    /**
     *
     * @return
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        // get all redis config
        return new StringRedisTemplate(lettuceConnectionFactory());
    }

    /**
     * init environment
     *
     * @param environment environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
        // bing binder
        binder = Binder.get(this.env);
    }
}
