# 介绍

集成多数据源，全局事物管理，`Druid`连接池，`Mybatis`，简化了多数据的复杂配置，
只需引入依赖后在`yaml`文件中配置多数据源连接信息即可。

# 引入依赖

- `Maven`
```xml
<dependency>
    <groupId>com.louislivi.fastdep</groupId>
    <artifactId>fastdep-datasource</artifactId>
    <version>${fastDepVersion}</version>
</dependency>
```
- `Gradle`
 ```groovy
 compile group: 'com.louislivi.fastdep', name: 'fastdep-datasource', version: '${fastDepVersion}'
```

# 配置文件

```yaml
fastdep:
  datasource:
    test: #数据源名称可随意取
      mapper: com.louislivi.fastdep.test.mapper.test #当前数据源对应的mapper目录不能多个数据源相同
      password: 123456
      url: jdbc:mysql://127.0.0.1:3306/douyin?serverTimezone=Asia/Chongqing&useLegacyDatetimeCode=false&nullNamePatternMatchesAll=true&zeroDateTimeBehavior=CONVERT_TO_NULL&tinyInt1isBit=false&autoReconnect=true&useSSL=false&pinGlobalTxToPhysicalConnection=true
      driverClassName: com.mysql.cj.jdbc.Driver
      username: root
#      # 下面为druid连接池的补充设置
#      initialSize: 10
#      minIdle: 5
#      maxActive: 100
#      connectionInitSqls: 'set names utf8mb4;'
    test2: #数据源名称可随意取
      mapper: com.louislivi.fastdep.test.mapper.test2 #当前数据源对应的mapper目录不能多个数据源相同
      password: 123456
      url: jdbc:mysql://127.0.0.1:3306/test2?serverTimezone=Asia/Chongqing&useLegacyDatetimeCode=false&nullNamePatternMatchesAll=true&zeroDateTimeBehavior=CONVERT_TO_NULL&tinyInt1isBit=false&autoReconnect=true&useSSL=false&pinGlobalTxToPhysicalConnection=true
      driverClassName: com.mysql.cj.jdbc.Driver
      username: root
#      # 下面为druid连接池的补充设置
#      initialSize: 10
#      minIdle: 5
#      maxActive: 100
#      connectionInitSqls: 'set names utf8mb4;'
```