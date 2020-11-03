package com.louislivi.fastdep.shirojwt.shiro;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.louislivi.fastdep.shirojwt.FastDepShiroJwtException;
import com.louislivi.fastdep.shirojwt.jwt.JwtToken;
import com.louislivi.fastdep.shirojwt.jwt.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * UserRealm
 *
 * @author : louislivi
 */
public class UserRealm extends AuthorizingRealm {
    private FastDepShiroJwtAuthorization fastDepShiroJwtAuthorization;
    private JwtUtil jwtUtil;

    public UserRealm(JwtUtil jwtUtil) {
        this.fastDepShiroJwtAuthorization = jwtUtil.fastDepShiroJwtAuthorization;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userId = jwtUtil.getUserId(principals.toString());
        return fastDepShiroJwtAuthorization.getAuthorizationInfo(userId);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String userId = jwtUtil.getUserId(token);
        if (userId == null) {
            throw new FastDepShiroJwtException("token invalid");
        }
        if (!fastDepShiroJwtAuthorization.verifyUser(userId, token)) {
            throw new FastDepShiroJwtException("verify user error!");
        }
        try {
            jwtUtil.verify(token, userId);
        } catch (JWTVerificationException e) {
            throw new FastDepShiroJwtException(e.getMessage());
        }
        return new SimpleAuthenticationInfo(token, token, "user_realm");
    }
}
