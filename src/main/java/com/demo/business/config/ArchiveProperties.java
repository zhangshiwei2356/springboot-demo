package com.demo.business.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 档案文件存储目录（相对应用工作目录，默认 ./data/archives）。
 */
@ConfigurationProperties(prefix = "demo.business.archive")
public class ArchiveProperties {

    private String uploadDir = "./data/archives";
    private long maxFileSize = 10 * 1024 * 1024;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
}
