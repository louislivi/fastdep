package com.louislivi.fastdep.shirojwt.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author louislivi
 */
public interface FastDepShiroJwtAuthorizationImp {
    /**
     * Get secret key according to user id
     *
     * @param userId user id
     * @return secret
     */
    String getSecret(String userId);

    /**
     * Get authorization info according to user id
     *
     * @param userId user id
     * @return authorizationInfo
     */
    SimpleAuthorizationInfo getAuthorizationInfo(String userId);

    /**
     * verify user legitimacy
     *
     * @param userId user id
     * @param token  token
     * @return boolean
     */
    boolean verifyUser(String userId, String token);

    /**
     * Authentication exception handel
     *
     * @param request                 ServletRequest
     * @param response                ServletResponse
     * @param authenticationException AuthenticationException
     */
    void authenticationExceptionHandel(ServletRequest request, ServletResponse response, AuthenticationException authenticationException);

    /**
     * Authorization exception handel
     *
     * @param request                ServletRequest
     * @param response               ServletResponse
     * @param authorizationException AuthenticationException
     */
    void authorizationExceptionHandel(HttpServletRequest request, HttpServletResponse response, AuthorizationException authorizationException);

    /**
     * Shiro filter factory bean setting
     *
     * @param factoryBean ShiroFilterFactoryBean
     */
    void shiroFilterFactoryBean(ShiroFilterFactoryBean factoryBean);
}
