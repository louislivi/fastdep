# 介绍

集成`Shiro`权限管理以及`JSON Web Token（JWT）`，实现基于`token`令牌的权限认证管理，
普通集成方式过于复杂并且容易出现未知的问题，这里将用最简单的方式进行集成。

# 引入依赖

- `Maven`
```xml
<dependency>
    <groupId>com.louislivi.fastdep</groupId>
    <artifactId>fastdep-shiro-jwt</artifactId>
    <version>${fastDepVersion}</version>
</dependency>
```
- `Gradle`
 ```groovy
 compile group: 'com.louislivi.fastdep', name: 'fastdep-shiro-jwt', version: '${fastDepVersion}'
```

# 配置文件

```yaml
fastdep:
  shiro-jwt:
    filter: #shiro过滤规则
      admin:
        path: /admin/**
        role: jwt # jwt为需要进行token校验
      front:
        path: /front/**/**
        role: anon # anon为无需校验
    secret: "6Dx8SIuaHXJYnpsG18SSpjPs50lZcT52" # jwt秘钥
#    expireTime: 7200000 # token有效期
#    prefix: "Bearer "  # token校验时的前缀
#    signPrefix: "Bearer " # token生成签名的前缀
#    header: "Authorization" # token校验时的header头
#    以下对应为shiro配置参数，无特殊需求无需配置
#    loginUrl: 
#    successUrl: 
#    unauthorizedUrl: 
#    filterChainDefinitions: 
```

# 用户权限配置

```java
@Component
public class FastDepShiroJwtConfig extends FastDepShiroJwtAuthorization {
    
    @Autowired
    private UserRequestDataMapper userRequestDataMapper;

    @Override
    public SimpleAuthorizationInfo getAuthorizationInfo(String userId) {
        // 查询该用户下的所有权限（当前为示例仅查询用户ID真实环境替换为用户的权限值）
        Set<String> collect = userRequestDataMapper.selectOptions().stream().map(u -> u.getUserId().toString()).collect(Collectors.toSet());
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        System.out.println(collect);
        // 当前值为 [1]
        // 添加用户权限到SimpleAuthorizationInfo中
        simpleAuthorizationInfo.addStringPermissions(collect);
        return simpleAuthorizationInfo;
    }
}
```

# 运用
```java
@RestController
public class TestController {
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 当前为示例所以直接返回了token，真实环境为校验登录信息后再返回token即可
     * @author : louislivi
     */
    @GetMapping("front/login")
    public String login() {
        // ...校验登录信息是否正确
        // 传入用户唯一标示
        return jwtUtil.sign("1"); 
    }

    /**
     * 当前为示例所以权限写的是用户ID 真实环境替换为权限key
     * @author : louislivi
     */
    @GetMapping("admin")
    @RequiresPermissions("1")
    public String jwt() {
        return "ok!";
    }
}
```

> 扩展

有时候需要自定义权限校验以及错误返回信息结构等，这时候就需要重写`FastDepShiroJwtAuthorization`类中的方法。

```java
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
     * 根据用户id来动态获取token秘钥，可以实现每个用户的秘钥不同安全性更高，通常可以用加密后的密码作为秘钥。
     *
     * @param userId user id
     * @return secret
     */
    @Override
    public String getSecret(String userId) {
        return null;
    }

    /**
     * 根据用户id获取权限信息
     *
     * @param userId user id
     * @return authorizationInfo
     */
    @Override
    public SimpleAuthorizationInfo getAuthorizationInfo(String userId) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 校验用户合法性扩展，可以根据用户id以及token扩展用户权限校验。
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
     * 认证校验失败回调
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
     * 权限校验失败回调
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
     * shiro过滤类扩展
     *
     * @param factoryBean ShiroFilterFactoryBean
     */
    @Override
    public void shiroFilterFactoryBean(ShiroFilterFactoryBean factoryBean) {

    }
}

```

还可以重写`JwtUtil`类实现token的校验生成等。

```java
package com.louislivi.fastdep.shirojwt.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.louislivi.fastdep.shirojwt.FastDepShiroJwt;
import com.louislivi.fastdep.shirojwt.shiro.FastDepShiroJwtAuthorization;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

import javax.annotation.Resource;
import java.util.Date;

import static org.apache.shiro.SecurityUtils.getSubject;


/**
 * JWTUtil
 *
 * @author : louislivi
 */
@ConditionalOnMissingBean(JwtUtil.class)
public class JwtUtil {
    @Resource
    public FastDepShiroJwtProperties fastDepShiroJwtProperties;
    @Resource
    public FastDepShiroJwtAuthorization fastDepShiroJwtAuthorization;

    /**
     * 校验token
     *
     * @param token token
     * @return true or false
     */
    public boolean verify(String token, String userId) {
        try {
            String secret = fastDepShiroJwtAuthorization.getSecret(userId) == null ? fastDepShiroJwtProperties.getSecret() : null;
            assert secret != null;
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(userId)
                    .build();
            verifier.verify(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 获取当前用户id
     *
     * @return user id
     */
    public String getUserId() {
        try {
            DecodedJWT jwt = JWT.decode(getSubject().getPrincipal().toString());
            return jwt.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据token获取用户id
     *
     * @param token jwt token
     * @return user id
     */
    public String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成token
     *
     * @param userId user identifier
     * @return token
     */
    public String sign(String userId) {
        Date date = new Date(System.currentTimeMillis() + fastDepShiroJwtProperties.getExpireTime());
        String secret = fastDepShiroJwtAuthorization.getSecret(userId) == null ? fastDepShiroJwtProperties.getSecret() : null;
        assert secret != null;
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return fastDepShiroJwtProperties.getSignPrefix() + JWT.create()
                .withSubject(userId)
                .withExpiresAt(date)
                .sign(algorithm);
    }
}
```