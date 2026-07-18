package com.demo.business.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 业务演示数据 JSON 存储目录（相对 business 进程工作目录，默认 ./data/demo）。
 */
@ConfigurationProperties(prefix = "demo.business.demo")
public class BusinessDataProperties {

    private String dataDir = "./data/demo";

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }
}
