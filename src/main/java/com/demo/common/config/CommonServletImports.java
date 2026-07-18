package com.demo.common.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * 仅在 Servlet MVC 环境下导入 Web 相关配置。
 */
public class CommonServletImports implements ImportSelector {

    private static final String WEB_MVC_CONFIGURER =
            "org.springframework.web.servlet.config.annotation.WebMvcConfigurer";

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        if (!ClassUtils.isPresent(WEB_MVC_CONFIGURER, null)) {
            return new String[0];
        }
        return new String[]{
                "com.demo.common.config.WebAutoConfig",
                "com.demo.common.aspect.RequestLogAspect",
                "com.demo.common.exception.GlobalExceptionHandler"
        };
    }
}
