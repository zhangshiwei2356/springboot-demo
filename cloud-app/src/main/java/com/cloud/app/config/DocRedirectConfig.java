package com.cloud.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 兼容原微服务各模块独立的 Knife4j 文档入口。
 */
@Configuration
public class DocRedirectConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/cloud-auth/doc.html", "/doc.html");
        registry.addRedirectViewController("/cloud-system/doc.html", "/doc.html");
        registry.addRedirectViewController("/cloud-business/doc.html", "/doc.html");
    }
}
