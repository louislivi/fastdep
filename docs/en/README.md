fastdep
============
[![release](https://img.shields.io/github/release/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/releases)
[![stars](https://img.shields.io/github/stars/louislivi/fastdep.svg?style=popout-square)](https://github.com/louislivi/fastdep/stargazers)
[![fastdep](https://img.shields.io/badge/fastdep-%F0%9F%92%97-pink.svg?style=popout-square)](https://github.com/louislivi/fastdep)
[![Apache License, Version 2.0](https://img.shields.io/github/license/apache/maven.svg?label=License)][license]

fastdep is a framework for fast integration dependencies,
integrate some common dependency injections.

Quick Build
-------
If you want to bootstrap fastdep, you'll need:
- Java 1.8+
- Spring Boot 2.0+
- Import dependency
    - Maven
    ```xml
    <dependency>
        <groupId>com.louislivi.fastdep</groupId>
        <artifactId>${Module Name}</artifactId>
        <version>${fastDepVersion}</version>
    </dependency>
    ```
    - Gradle
    ```groovy
    compile group: 'com.louislivi.fastdep', name: '${Module Name}', version: '${fastDepVersion}'
    ```
  
Modules
-------
|  Module Name  |  Description  | Include dependency |
| ------------ | ------------- | ------------------ |
| fastdep-datasource   | Multiple dataSource | jta+druid+mybatis |
| fastdep-redis   | Multiple redis dataSource  | redis+redisTemplate |

Documentation
-------------

More information can be found on [Fastdep Homepage][home].

License
-------
This code is under the [Apache Licence v2][license]


[home]: https://fastdep.louislivi.com/
[license]: https://www.apache.org/licenses/LICENSE-2.0
[releases]: https://github.com/louislivi/fastdep/releases
