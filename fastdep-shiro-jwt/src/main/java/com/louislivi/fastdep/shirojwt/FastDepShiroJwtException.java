package com.louislivi.fastdep.shirojwt;

import org.apache.shiro.authc.AuthenticationException;

/**
 * @author : louislivi
 */
public class FastDepShiroJwtException extends AuthenticationException {

    public FastDepShiroJwtException() {
    }

    public FastDepShiroJwtException(String message) {
        super(message);
    }

    public FastDepShiroJwtException(Throwable cause) {
        super(cause);
    }

    public FastDepShiroJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
