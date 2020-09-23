中文 | [English](./README-EN.md)
```
     __                    _         _             _ __  
    / _|  __ _     ___    | |_    __| |    ___    | '_ \ 
   |  _| / _` |   (_-<    |  _|  / _` |   / -_)   | .__/ 
  _|_|_  \__,_|   /__/_   _\__|  \__,_|   \___|   |_|__  
_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""| 
"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-' 
```
fastdep
============
[![release](https://img.shields.io/github/release/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/releases)
[![stars](https://img.shields.io/github/stars/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/stargazers)
[![fastdep](https://img.shields.io/badge/fastdep-%F0%9F%92%97-pink.svg?style=popout-square)](https://github.com/louislivi/fastdep)
[![Apache License, Version 2.0](https://img.shields.io/github/license/apache/maven.svg?label=License)][license]

`fastdep`是一个快速集成依赖的框架，集成了一些常用公共的依赖。

快速开始
-------
如果你想使用`fastdep`,你需要：
- Java 1.8+
- Spring Boot 2.0+
- 引入依赖
    - Maven
    ```xml
    <dependency>
        <groupId>com.louislivi.fastdep</groupId>
        <artifactId>${模块名称}</artifactId>
        <version>1.0.4</version>
    </dependency>
    ```
    - Gradle
    ```groovy
    compile group: 'com.louislivi.fastdep', name: '${Module Name}', version: '1.0.4'
    ```
  
模块
-------
|  模块名称  |  描述  | 引入的依赖 |
| ------------ | ------------- | ------------------ |
| [fastdep-datasource](https://fastdep.louislivi.com/#/module/fastdep-datasource)   | 多数据源 | JTA+Druid+Mybatis |
| [fastdep-redis](https://fastdep.louislivi.com/#/module/fastdep-redis)   | Redis多数据源  | Redis+RedisTemplate |
| [fastdep-shiro-jwt](https://fastdep.louislivi.com/#/module/fastdep-shiro-jwt)   | ShiroJWT  | Shiro+JWT |
| [fastdep-file](https://fastdep.louislivi.com/#/module/fastdep-file)   | 文件上传下载  | 无 |

文档
-------

更多信息可以在 [Fastdep 首页][home] 中找到.

协议
-------
[Apache Licence v2][license]


[home]: https://fastdep.louislivi.com/
[license]: https://www.apache.org/licenses/LICENSE-2.0
[releases]: https://github.com/louislivi/fastdep/releases
