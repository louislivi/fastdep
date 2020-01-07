package com.louislivi.fastdep.shirojwt;

import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import com.louislivi.fastdep.shirojwt.shiro.FastDepShiroJwtAuthorization;
import com.louislivi.fastdep.shirojwt.shiro.ShiroConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * FastDepShiroJwtAutoConfiguration
 *
 * @author : louislivi
 */
@Configuration
@EnableConfigurationProperties({FastDepShiroJwtProperties.class})
@Import({ShiroConfig.class, JwtUtil.class, FastDepShiroJwtAuthorization.class})
public class FastDepShiroJwtAutoConfiguration {
}
