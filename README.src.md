<!--@nrg.languages=zh,en-->
<!--@nrg.defaultLanguage=zh-->
<!--@nrg.fileNamePattern.en=README-EN.md-->
中文 | [English](./README-EN.md)<!--zh-->
```<!--zh-->
     __                    _         _             _ __  <!--zh-->
    / _|  __ _     ___    | |_    __| |    ___    | '_ \ <!--zh-->
   |  _| / _` |   (_-<    |  _|  / _` |   / -_)   | .__/ <!--zh-->
  _|_|_  \__,_|   /__/_   _\__|  \__,_|   \___|   |_|__  <!--zh-->
_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""| <!--zh-->
"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-' <!--zh-->
```<!--zh-->
fastdep<!--zh-->
============<!--zh-->
[![release](https://img.shields.io/github/release/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/releases)<!--zh-->
[![stars](https://img.shields.io/github/stars/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/stargazers)<!--zh-->
[![fastdep](https://img.shields.io/badge/fastdep-%F0%9F%92%97-pink.svg?style=popout-square)](https://github.com/louislivi/fastdep)<!--zh-->
[![Apache License, Version 2.0](https://img.shields.io/github/license/apache/maven.svg?label=License)][license]<!--zh-->
<!--zh-->
`fastdep`是一个快速集成依赖的框架，集成了一些常用公共的依赖。<!--zh-->
<!--zh-->
快速开始<!--zh-->
-------<!--zh-->
如果你想使用`fastdep`,你需要：<!--zh-->
- Java 1.8+<!--zh-->
- Spring Boot 2.0+<!--zh-->
- 引入依赖<!--zh-->
    - Maven<!--zh-->
    ```xml<!--zh-->
    <dependency><!--zh-->
        <groupId>com.louislivi.fastdep</groupId><!--zh-->
        <artifactId>${模块名称}</artifactId><!--zh-->
        <version>1.0.7</version><!--zh-->
    </dependency><!--zh-->
    ```<!--zh-->
    - Gradle<!--zh-->
    ```groovy<!--zh-->
    compile group: 'com.louislivi.fastdep', name: '${Module Name}', version: '1.0.7'<!--zh-->
    ```<!--zh-->
  <!--zh-->
模块<!--zh-->
-------<!--zh-->
|  模块名称  |  描述  | 引入的依赖 |<!--zh-->
| ------------ | ------------- | ------------------ |<!--zh-->
| [fastdep-datasource](https://fastdep.louislivi.com/#/module/fastdep-datasource)   | 多数据源 | JTA+Druid+Mybatis |<!--zh-->
| [fastdep-redis](https://fastdep.louislivi.com/#/module/fastdep-redis)   | Redis多数据源  | Redis+RedisTemplate |<!--zh-->
| [fastdep-shiro-jwt](https://fastdep.louislivi.com/#/module/fastdep-shiro-jwt)   | ShiroJWT  | Shiro+JWT |<!--zh-->
| [fastdep-file](https://fastdep.louislivi.com/#/module/fastdep-file)   | 文件上传下载  | 无 |<!--zh-->
<!--zh-->
文档<!--zh-->
-------<!--zh-->
<!--zh-->
更多信息可以在 [Fastdep 首页][home] 中找到.<!--zh-->
<!--zh-->
协议<!--zh-->
-------<!--zh-->
[Apache Licence v2][license]<!--zh-->
<!--zh-->
<!--zh-->
[home]: https://fastdep.louislivi.com/<!--zh-->
[license]: https://www.apache.org/licenses/LICENSE-2.0<!--zh-->
[releases]: https://github.com/louislivi/fastdep/releases<!--zh-->
English | [中文](./README.md)<!--en-->
```<!--en-->
     __                    _         _             _ __  <!--en-->
    / _|  __ _     ___    | |_    __| |    ___    | '_ \ <!--en-->
   |  _| / _` |   (_-<    |  _|  / _` |   / -_)   | .__/ <!--en-->
  _|_|_  \__,_|   /__/_   _\__|  \__,_|   \___|   |_|__  <!--en-->
_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""| <!--en-->
"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-' <!--en-->
```<!--en-->
fastdep<!--en-->
============<!--en-->
[![release](https://img.shields.io/github/release/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/releases)<!--en-->
[![stars](https://img.shields.io/github/stars/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/stargazers)<!--en-->
[![fastdep](https://img.shields.io/badge/fastdep-%F0%9F%92%97-pink.svg?style=popout-square)](https://github.com/louislivi/fastdep)<!--en-->
[![Apache License, Version 2.0](https://img.shields.io/github/license/apache/maven.svg?label=License)][license]<!--en-->
<!--en-->
fastdep is a framework for fast integration dependencies,<!--en-->
integrate some common dependency injections.<!--en-->
<!--en-->
Quick Build<!--en-->
-------<!--en-->
If you want to bootstrap fastdep, you'll need:<!--en-->
- Java 1.8+<!--en-->
- Spring Boot 2.0+<!--en-->
- Import dependency<!--en-->
    - Maven<!--en-->
    ```xml<!--en-->
    <dependency><!--en-->
        <groupId>com.louislivi.fastdep</groupId><!--en-->
        <artifactId>${Module Name}</artifactId><!--en-->
        <version>1.0.7</version><!--en-->
    </dependency><!--en-->
    ```<!--en-->
    - Gradle<!--en-->
    ```groovy<!--en-->
    compile group: 'com.louislivi.fastdep', name: '${Module Name}', version: '1.0.7'<!--en-->
    ```<!--en-->
  <!--en-->
Modules<!--en-->
-------<!--en-->
|  Module Name  |  Description  | Include dependency |<!--en-->
| ------------ | ------------- | ------------------ |<!--en-->
| [fastdep-datasource](https://fastdep.louislivi.com/#/module/fastdep-datasource)   | Multiple dataSource | jta+druid+mybatis |<!--en-->
| [fastdep-redis](https://fastdep.louislivi.com/#/module/fastdep-redis)   | Multiple redis dataSource  | redis+redisTemplate |<!--en-->
| [fastdep-shiro-jwt](https://fastdep.louislivi.com/#/module/fastdep-shiro-jwt)   | ShiroJWT  | Shiro+JWT |<!--en-->
| [fastdep-file](https://fastdep.louislivi.com/#/module/fastdep-file)   | File upload & download  | None |<!--en-->
<!--en-->
Documentation<!--en-->
-------------<!--en-->
<!--en-->
More information can be found on [Fastdep Homepage][home].<!--en-->
<!--en-->
License<!--en-->
-------<!--en-->
This code is under the [Apache Licence v2][license]<!--en-->
<!--en-->
<!--en-->
[home]: https://fastdep.louislivi.com/<!--en-->
[license]: https://www.apache.org/licenses/LICENSE-2.0<!--en-->
[releases]: https://github.com/louislivi/fastdep/releases<!--en-->
