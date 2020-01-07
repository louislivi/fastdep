package com.louislivi.fastdep.file;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.servlet.MultipartConfigElement;

/**
 * FastDepFileProperties
 *
 * @author : louislivi
 */
@ConditionalOnProperty("fastdep.file")
@ConfigurationProperties("fastdep.file")
public class FastDepFileProperties {
    private String baseDir = "./uploads";
    private String uploadDir = "";
    private String urlPrefix = "/fd/file";
    private Scheme scheme = Scheme.HTTP;
    private String downloadUrl = "/download";
    private String uploadUrl = "/upload";
    private String fieldName = "file";
    private Boolean originName = false;
    private Config config = new Config();

    public static class Config {
        private String location = "";
        private Long maxFileSize = -1L;
        private Long maxRequestSize = -1L;
        private Integer fileSizeThreshold = 0;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Long getMaxFileSize() {
            return maxFileSize;
        }

        public void setMaxFileSize(Long maxFileSize) {
            this.maxFileSize = maxFileSize;
        }

        public Long getMaxRequestSize() {
            return maxRequestSize;
        }

        public void setMaxRequestSize(Long maxRequestSize) {
            this.maxRequestSize = maxRequestSize;
        }

        public Integer getFileSizeThreshold() {
            return fileSizeThreshold;
        }

        public void setFileSizeThreshold(Integer fileSizeThreshold) {
            this.fileSizeThreshold = fileSizeThreshold;
        }

        public MultipartConfigElement getMultipartConfig() {
            return new MultipartConfigElement(this.getLocation(), this.getMaxFileSize(), this.getMaxRequestSize(), this.getFileSizeThreshold());
        }
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getScheme() {
        return scheme.toString().toLowerCase();
    }

    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    public String getDownloadUrl() {
        return urlPrefix + downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUploadUrl() {
        return urlPrefix + uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public Boolean getOriginName() {
        return originName;
    }

    public void setOriginName(Boolean originName) {
        this.originName = originName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public enum Scheme {
        /**
         * scheme
         */
        HTTP, HTTPS;
    }
}
