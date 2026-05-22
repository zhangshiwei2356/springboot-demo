package com.cloud.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 单机应用启动类：聚合认证、系统、业务及 Web 能力。
 */
@SpringBootApplication(scanBasePackages = "com.cloud")
public class CloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class, args);
    }
}
