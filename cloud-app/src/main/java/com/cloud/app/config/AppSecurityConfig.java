package com.cloud.app.config;

import com.cloud.app.filter.JwtAuthFilter;
import com.cloud.app.security.AppSecurityProperties;
import com.cloud.common.util.JwtUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@EnableConfigurationProperties(AppSecurityProperties.class)
public class AppSecurityConfig {

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilter(JwtUtil jwtUtil, AppSecurityProperties props) {
        FilterRegistrationBean<JwtAuthFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new JwtAuthFilter(jwtUtil, props));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        bean.addUrlPatterns("/*");
        return bean;
    }
}
