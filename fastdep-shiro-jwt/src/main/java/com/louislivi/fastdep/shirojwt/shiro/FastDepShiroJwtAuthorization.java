package com.louislivi.fastdep.shirojwt.shiro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * FastDepShiroJwtAuthorization
 *
 * @author : louislivi
 */
@RestControllerAdvice
@ConditionalOnMissingBean(FastDepShiroJwtAuthorization.class)
public class FastDepShiroJwtAuthorization implements FastDepShiroJwtAuthorizationImp {

    /**
     * Get secret key according to user id
     *
     * @param userId user id
     * @return secret
     */
    @Override
    public String getSecret(String userId) {
        return null;
    }

    /**
     * Get authorization info according to user id
     *
     * @param userId user id
     * @return authorizationInfo
     */
    @Override
    public SimpleAuthorizationInfo getAuthorizationInfo(String userId) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * verify user legitimacy
     *
     * @param userId user id
     * @param token  token
     * @return boolean
     */
    @Override
    public boolean verifyUser(String userId, String token) {
        return true;
    }

    /**
     * Authentication exception handel
     *
     * @param request                 ServletRequest
     * @param response                ServletResponse
     * @param authenticationException AuthenticationException
     */
    @Override
    public void authenticationExceptionHandel(ServletRequest request, ServletResponse response, AuthenticationException authenticationException) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonObject = new HashMap<>(2);
        jsonObject.put("code", 401);
        jsonObject.put("msg", authenticationException.getMessage());
        try {
            String result = mapper.writeValueAsString(jsonObject);
            PrintWriter out = null;
            HttpServletResponse res = (HttpServletResponse) response;
            try {
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json");
                out = response.getWriter();
                out.println(result);
            } catch (Exception ignored) {
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
        } catch (JsonProcessingException ignored) {
        }
    }

    /**
     * Authorization exception handel
     *
     * @param request                ServletRequest
     * @param response               ServletResponse
     * @param authorizationException AuthenticationException
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @Override
    public void authorizationExceptionHandel(HttpServletRequest request, HttpServletResponse response, AuthorizationException authorizationException) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonObject = new HashMap<>(2);
        jsonObject.put("code", 403);
        jsonObject.put("msg", authorizationException.getMessage());
        try {
            String result = mapper.writeValueAsString(jsonObject);
            PrintWriter out = null;
            try {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                out = response.getWriter();
                out.println(result);
            } catch (Exception ignored) {
            } finally {
                if (null != out) {
                    out.flush();
                    out.close();
                }
            }
        } catch (JsonProcessingException ignored) {
        }
    }

    /**
     * Shiro filter factory bean setting
     *
     * @param factoryBean ShiroFilterFactoryBean
     */
    @Override
    public void shiroFilterFactoryBean(ShiroFilterFactoryBean factoryBean) {

    }
}
