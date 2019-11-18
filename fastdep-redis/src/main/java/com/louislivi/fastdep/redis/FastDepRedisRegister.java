package com.louislivi.fastdep.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * FastDepRedis Register
 *
 * @author : louislivi
 */
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
        //TODO
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
