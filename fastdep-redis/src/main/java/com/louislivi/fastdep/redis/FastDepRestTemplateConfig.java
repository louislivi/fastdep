package com.louislivi.fastdep.redis;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * RestTemplate Config
 *
 * @author : louislivi
 */
public class FastDepRestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                //建立连接的超时时间
                .setConnectTimeout(Duration.ofSeconds(20))
                //传递数据的超时时间
                .setReadTimeout(Duration.ofSeconds(20))
                .build();
    }
}
