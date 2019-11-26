# fastdep

[![release](https://img.shields.io/github/release/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/releases)
[![stars](https://img.shields.io/github/stars/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/stargazers)
[![fastdep](https://img.shields.io/badge/fastdep-%F0%9F%92%97-pink.svg?style=popout-square)](https://github.com/louislivi/fastdep)
[![Apache License, Version 2.0](https://img.shields.io/github/license/apache/maven.svg?label=License)][license]

> `Github` 项目地址：[https://github.com/louislivi/fastdep](https://github.com/louislivi/fastdep) (支持请点Star)

# 设计初衷

你是否也发现`Spring Boot`集成一些依赖时过于复杂，
需要重写或者建立一些`java`配置类进行配置，
有些多个依赖同时集成还易出现各种兼容性问题，
`fastdep`只需要引入一个依赖就可以解决你的这些问题。

后续还会继续开发更多的依赖集成有兴趣的`coder`可以贡献你的代码让集成依赖更加的方便。

# 快速开始

如果你想使用`fastdep`,你需要：
- Java 1.8+
- Spring Boot 2.0+
- 引入依赖
    - Maven
    ```xml
    <dependency>
        <groupId>com.louislivi.fastdep</groupId>
        <artifactId>${模块名称}</artifactId>
        <version>${fastDepVersion}</version>
    </dependency>
    ```
    - Gradle
    ```groovy
    compile group: 'com.louislivi.fastdep', name: '${Module Name}', version: '${fastDepVersion}'
    ```
  
# 模块

|  模块名称  |  描述  | 引入的依赖 |
| ------------ | ------------- | ------------------ |
| [fastdep-datasource](module/fastdep-datasource.md)   | 多数据源 | jta+druid+mybatis |
|  [fastdep-redis](module/fastdep-redis.md)  | Redis多数据源  | redis+redisTemplate |


# 协议

[Apache Licence v2][license]


[home]: https://fastdep.louislivi.com/
[license]: https://www.apache.org/licenses/LICENSE-2.0
[releases]: https://github.com/louislivi/fastdep/releases
