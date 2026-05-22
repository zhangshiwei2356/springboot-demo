package com.cloud.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 保持与原网关路由一致的前缀：/cloud-auth、/cloud-system、/cloud-business。
 */
@Configuration
public class ModulePathPrefixConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/cloud-auth",
                c -> c.isAnnotationPresent(RestController.class)
                        && c.getPackageName().startsWith("com.cloud.auth"));
        configurer.addPathPrefix("/cloud-system",
                c -> c.isAnnotationPresent(RestController.class)
                        && c.getPackageName().startsWith("com.cloud.system"));
        configurer.addPathPrefix("/cloud-business",
                c -> c.isAnnotationPresent(RestController.class)
                        && c.getPackageName().startsWith("com.cloud.business"));
    }
}
