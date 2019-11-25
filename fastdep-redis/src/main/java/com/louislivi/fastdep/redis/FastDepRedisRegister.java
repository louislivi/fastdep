package com.louislivi.fastdep.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
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
 * FastDepRedis Register
 *
 * @author : louislivi
 */
@SuppressWarnings("unchecked")
public class FastDepRedisRegister implements EnvironmentAware, ImportBeanDefinitionRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(FastDepRedisRegister.class);

    private Environment env;

    private Binder binder;

    /**
     * ImportBeanDefinitionRegistrar
     *
     * @param annotationMetadata     annotationMetadata
     * @param beanDefinitionRegistry beanDefinitionRegistry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // get all redis config
        Map<String, Map> multipleRedis;
        multipleRedis = binder.bind("fastdep.redis", Map.class).get();
        for (String key : multipleRedis.keySet()) {
            Map map = binder.bind("fastdep.redis." + key, Map.class).get();
            // RedisStandaloneConfiguration
            RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setHostName((String) map.get("host"));
            configuration.setPort((Integer) map.get("port"));
            configuration.setDatabase((Integer) map.get("database"));
            String password = (String) map.get("password");
            if (!StringUtils.isEmpty(password)) {
                RedisPassword redisPassword = RedisPassword.of(password);
                configuration.setPassword(redisPassword);
            }
            // GenericObjectPoolConfig
            GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
            Map<String, Integer> pool = binder.bind("fastdep.redis." + key + ".lettuce.pool", Map.class).get();
            genericObjectPoolConfig.setMaxIdle(pool.get("maxIdle"));
            Duration shutdownTimeout = binder.bind("fastdep.redis." + key + ".shutdown-timeout", Duration.class).get();
            LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
            if (shutdownTimeout != null) {
                builder.shutdownTimeout(shutdownTimeout);
            }
            LettuceClientConfiguration clientConfiguration = builder.poolConfig(genericObjectPoolConfig).build();
            LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration, clientConfiguration);
            lettuceConnectionFactory.afterPropertiesSet();
            GenericBeanDefinition stringRedisTemplate = new GenericBeanDefinition();
            stringRedisTemplate.setBeanClass(StringRedisTemplate.class);
            ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0, lettuceConnectionFactory);
            stringRedisTemplate.setConstructorArgumentValues(constructorArgumentValues);
            beanDefinitionRegistry.registerBeanDefinition(key + "StringRedisTemplate", stringRedisTemplate);
            logger.info("Registration redis ({}) !", key);
        }
        logger.info("Registration redis completed !");
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
