package com.louislivi.fastdep.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * FastDepFileAutoConfiguration
 *
 * @author : louislivi
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(FastDepFileProperties.class)
public class FastDepFileAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(FastDepFileAutoConfiguration.class);

    @Bean
    public ServletRegistrationBean<FastDepFileServlet> fastDepFileServlet(FastDepFileProperties fastDepFileProperties) {
        logger.info("FastDepFileServlet registration.");
        ServletRegistrationBean<FastDepFileServlet> registrationBean = new ServletRegistrationBean<>(new FastDepFileServlet(fastDepFileProperties));
        registrationBean.addUrlMappings(fastDepFileProperties.getDownloadUrl() + File.separator + "*", fastDepFileProperties.getUploadUrl());
        registrationBean.setMultipartConfig(fastDepFileProperties.getConfig().getMultipartConfig());
        return registrationBean;
    }
}
