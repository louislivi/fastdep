package com.louislivi.fastdep.shirojwt.shiro;

import com.louislivi.fastdep.shirojwt.FastDepShiroJwtProperties;
import com.louislivi.fastdep.shirojwt.jwt.JwtFilter;
import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ShiroConfig
 *
 * @author : louislivi
 */
public class ShiroConfig {

    /**
     * AuthorizingRealm
     *
     * @param jwtUtil jwt util bean
     * @return AuthorizingRealm
     */
    @Bean
    @ConditionalOnMissingBean(AuthorizingRealm.class)
    public AuthorizingRealm authorizingRealm(JwtUtil jwtUtil) {
        return new UserRealm(jwtUtil);
    }

    /**
     * securityManager
     *
     * @param authorizingRealm AuthorizingRealm bean
     * @return securityManager
     */
    @Bean("securityManager")
    @ConditionalOnMissingBean(SecurityManager.class)
    public DefaultWebSecurityManager getManager(AuthorizingRealm authorizingRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authorizingRealm);
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }

    /**
     * shiroFilter
     *
     * @param securityManager securityManager bean
     * @param jwtUtil         jwt util bean
     * @return shiroFilter
     */
    @Bean("shiroFilter")
    @ConditionalOnMissingBean(ShiroFilter.class)
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager, JwtUtil jwtUtil) {
        FastDepShiroJwtProperties fastDepShiroJwtProperties = jwtUtil.fastDepShiroJwtProperties;
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // define your filter and name it as jwt
        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("jwt", new JwtFilter(jwtUtil));
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        /*
         * difine custom URL rule
         * http://shiro.apache.org/web.html#urls-
         */
        Map<String, FastDepShiroJwtProperties.ShiroRole> filter = fastDepShiroJwtProperties.getFilter();
        if (filter.size() > 0) {
            LinkedHashMap<String, String> filterRuleMap = filter.values().stream().
                    collect(Collectors.toMap(FastDepShiroJwtProperties.ShiroRole::getPath,
                            FastDepShiroJwtProperties.ShiroRole::getRole, (key1, key2) -> key2, LinkedHashMap::new));
            // 401 and 404 page does not forward to our filter
            factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        }
        if (fastDepShiroJwtProperties.getFilterChainDefinitions() != null) {
            factoryBean.setFilterChainDefinitions(fastDepShiroJwtProperties.getFilterChainDefinitions());
        }
        factoryBean.setLoginUrl(fastDepShiroJwtProperties.getLoginUrl());
        factoryBean.setSuccessUrl(fastDepShiroJwtProperties.getSuccessUrl());
        factoryBean.setUnauthorizedUrl(fastDepShiroJwtProperties.getUnauthorizedUrl());
        jwtUtil.fastDepShiroJwtAuthorization.shiroFilterFactoryBean(factoryBean);
        return factoryBean;
    }

    /**
     * LifecycleBeanPostProcessor
     *
     * @return LifecycleBeanPostProcessor
     */
    @Bean
    @ConditionalOnMissingBean(LifecycleBeanPostProcessor.class)
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * AuthorizationAttributeSourceAdvisor
     *
     * @param securityManager securityManager bean
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    @ConditionalOnMissingBean(AuthorizationAttributeSourceAdvisor.class)
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
