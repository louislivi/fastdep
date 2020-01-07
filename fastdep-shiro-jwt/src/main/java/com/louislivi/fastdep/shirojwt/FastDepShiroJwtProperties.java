package com.louislivi.fastdep.shirojwt;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * FastDepShiroJwt
 *
 * @author : louislivi
 */
@Configuration
@ConditionalOnProperty("fastdep.shiro-jwt")
@ConfigurationProperties("fastdep.shiro-jwt")
public class FastDepShiroJwtProperties {
    /**
     * shiro filter
     */
    private Map<String, ShiroRole> filter = new LinkedHashMap<>();
    /**
     * secret
     */
    private String secret = "";
    /**
     * token expire time
     */
    private Long expireTime = 7200000L;

    private String loginUrl;

    private String successUrl;

    private String unauthorizedUrl;

    private String filterChainDefinitions;
    /**
     * token prefix
     */
    private String prefix = "Bearer ";
    /**
     * sign token prefix
     */
    private String signPrefix = prefix;
    /**
     * token header field
     */
    private String header = "Authorization";


    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public Map<String, ShiroRole> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, ShiroRole> filter) {
        this.filter = filter;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getFilterChainDefinitions() {
        return filterChainDefinitions;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSignPrefix() {
        return signPrefix;
    }

    public void setSignPrefix(String signPrefix) {
        this.signPrefix = signPrefix;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "FastDepShiroJwt{" +
                "filter=" + filter +
                ", secret='" + secret + '\'' +
                ", expireTime=" + expireTime +
                ", loginUrl='" + loginUrl + '\'' +
                ", successUrl='" + successUrl + '\'' +
                ", unauthorizedUrl='" + unauthorizedUrl + '\'' +
                ", filterChainDefinitions='" + filterChainDefinitions + '\'' +
                ", prefix='" + prefix + '\'' +
                ", signPrefix='" + signPrefix + '\'' +
                ", header='" + header + '\'' +
                '}';
    }

    public static class ShiroRole {
        private String path;
        private String role;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return "ShiroRole{" +
                    "path='" + path + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }
    }
}
