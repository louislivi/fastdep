# 介绍

集成后将自动实现文件上传以及下载功能API

# 引入依赖

- `Maven`
```xml
<dependency>
    <groupId>com.louislivi.fastdep</groupId>
    <artifactId>fastdep-file</artifactId>
    <version>${fastDepVersion}</version>
</dependency>
```
- `Gradle`
 ```groovy
 compile group: 'com.louislivi.fastdep', name: 'fastdep-file', version: '${fastDepVersion}'
```

# 配置文件

```yaml
fastdep:
  file:
    originName: true #按文件原名存储（默认文件名为UUID）
#    scheme: HTTP # 连接协议 HTTP|HTTPS
#    uploadDir: "/{yyyy}/{MM}/{dd}" # 文件上传路径支持{}定义日期目录
#    baseDir: "./uploads"; # 文件上传根路径
#    urlPrefix: "/fd/file"; # 文件上传下载url前缀
#    downloadUrl: "/download"; # 文件下载url地址
#    uploadUrl:  "/upload"; # 文件上传url地址
#    fieldName: "file"; # 文件上传字段名
#    config: # javax.servlet.annotation.MultipartConfig 文件上传配置
#      location: ""
#      maxFileSize: -1
#      maxRequestSize: -1
#      fileSizeThreshold: 0
```

# 运用

使用CURL上传文件

```bash
➜  ~ curl 127.0.0.1:9999/fd/file/upload -F "file=@/Users/yons/Downloads/test.jpg" -v
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9999 (#0)
> POST /fd/file/upload HTTP/1.1
> Host: 127.0.0.1:9999
> User-Agent: curl/7.54.0
> Accept: */*
> Content-Length: 7927
> Expect: 100-continue
> Content-Type: multipart/form-data; boundary=------------------------6a12ae0fc9faa7de
>
< HTTP/1.1 100
< HTTP/1.1 200
< Content-Type: text/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Sat, 11 Jan 2020 09:35:45 GMT
<
{"fileName":"test.jpg","fileDownloadUri":"http://127.0.0.1:9999/fd/file/download/2020/01/11/test.jpg","originName":"test.jpg","size":7741}
* Connection #0 to host 127.0.0.1 left intact
```

随后访问请求返回的`fileDownloadUri`即可下载或预览文件