package com.demo.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 模块 API 路径前缀：/auth、/system、/business。
 */
@Configuration
public class ModulePathPrefixConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/auth",
                c -> c.isAnnotationPresent(RestController.class)
                        && c.getPackageName().startsWith("com.demo.auth"));
        configurer.addPathPrefix("/system",
                c -> c.isAnnotationPresent(RestController.class)
                        && c.getPackageName().startsWith("com.demo.system"));
        configurer.addPathPrefix("/business",
                c -> c.isAnnotationPresent(RestController.class)
                        && c.getPackageName().startsWith("com.demo.business"));
    }
}
