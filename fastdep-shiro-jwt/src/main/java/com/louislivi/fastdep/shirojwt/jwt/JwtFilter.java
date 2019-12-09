package com.louislivi.fastdep.shirojwt.jwt;

import com.louislivi.fastdep.shirojwt.FastDepShiroJwtException;
import com.louislivi.fastdep.shirojwt.shiro.FastDepShiroJwtAuthorization;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * JWTFilter
 *
 * @author : louislivi
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private FastDepShiroJwtAuthorization fastDepShiroJwtAuthorization;
    private JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.fastDepShiroJwtAuthorization = jwtUtil.fastDepShiroJwtAuthorization;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(jwtUtil.fastDepShiroJwt.getHeader());
        return authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(jwtUtil.fastDepShiroJwt.getHeader());
        authorization = authorization.replaceAll("(?i)" + jwtUtil.fastDepShiroJwt.getPrefix(), "");
        JwtToken token = new JwtToken(authorization);
        //verify token
        String userId = jwtUtil.getUserId(authorization);
        if (userId == null) {
            return false;
        }
        getSubject(request, response).login(token);
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                return executeLogin(request, response);
            } catch (AuthenticationException e) {
                this.fastDepShiroJwtAuthorization.authenticationExceptionHandel(request, response, e);
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws AuthenticationException {
        this.fastDepShiroJwtAuthorization.authenticationExceptionHandel(request, response, new FastDepShiroJwtException("Access denied !"));
        return false;
    }
}
