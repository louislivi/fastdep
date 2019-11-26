# 介绍

集成`Redis`多数据源并集成`lettuce`连接池，
只需引入依赖后在`yaml`文件中配置多数据源连接信息即可。
# 引入依赖

- `Maven`
```xml
<dependency>
    <groupId>com.louislivi.fastdep</groupId>
    <artifactId>fastdep-redis</artifactId>
    <version>${fastDepVersion}</version>
</dependency>
```
- `Gradle`
 ```groovy
 compile group: 'com.louislivi.fastdep', name: 'fastdep-redis', version: '${fastDepVersion}'
```

# 配置文件

```yaml
fastdep:
    redis:
      redis1: #连接名称
        database: 0
        host: 192.168.12.88
        port: 6379
        lettuce: #下面为连接池的补充设置
          shutdown-timeout: 100 # 关闭超时时间
          pool:
            max-active: 18 # 连接池最大连接数（使用负值表示没有限制）
            max-idle: 8 # 连接池中的最大空闲连接
            max-wait: 30 # 连接池最大阻塞等待时间（使用负值表示没有限制）
            min-idle: 0 # 连接池中的最小空闲连接
      redis2: #连接名称
        database: 1
        host: 192.168.12.88
        port: 6379
        lettuce: #下面为连接池的补充设置
          shutdown-timeout: 100 # 关闭超时时间
          pool:
            max-active: 18 # 连接池最大连接数（使用负值表示没有限制）
            max-idle: 8 # 连接池中的最大空闲连接
            max-wait: 30 # 连接池最大阻塞等待时间（使用负值表示没有限制）
            min-idle: 0 # 连接池中的最小空闲连接
```

# 运用

```java
@Autowired
private StringRedisTemplate redis1StringRedisTemplate;
// 注入时 redis1 代表配置文件中的连接名称 StringRedisTemplate 为固定注入redis对象类型,
// 会自动根据注入的变量名进行匹配

@Autowired
private StringRedisTemplate redis2StringRedisTemplate;


@GetMapping("redis")
public void redis() {
    System.out.println(redis1StringRedisTemplate.opsForValue().get("test"));
    System.out.println(redis2StringRedisTemplate.opsForValue().get("test"));
}
```
> 扩展

有时候需要自定义`redisTemplate`序列化和增加一些额外的配置，这时候我们可以封装一个`redis`工具类来实现

```java
package com.louislivi.fastdep.test.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * RedisUtil
 * 
 * @author : louislivi
 */
@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate redis1StringRedisTemplate;

    @Autowired
    private StringRedisTemplate redis2StringRedisTemplate;

    @Autowired
    private RedisTemplate redis2RedisTemplate;

    @Autowired
    private RedisTemplate redis1RedisTemplate;

    public RedisTemplate redisTemplate(String name) {
        RedisTemplate redisTemplate;
        switch (name) {
            case "redis2":
                redisTemplate = redis2RedisTemplate;
                break;
            default:
                redisTemplate = redis1RedisTemplate;
                break;
        }
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }

    public StringRedisTemplate stringRedisTemplate(String name) {
        StringRedisTemplate stringRedisTemplate;
        switch (name) {
            case "redis2":
                stringRedisTemplate = redis2StringRedisTemplate;
                break;
            default:
                stringRedisTemplate = redis1StringRedisTemplate;
                break;
        }
        stringRedisTemplate.setEnableTransactionSupport(true);
        return stringRedisTemplate;
    }
}

```

```java
@Autowired
private RedisUtil redisUtil;


@GetMapping("redis")
public void redis() {
    System.out.println(redisUtil.redisTemplate("redis1").opsForValue().get("test"));
    System.out.println(redisUtil.stringRedisTemplate("redis2").opsForValue().get("test"));
}
```