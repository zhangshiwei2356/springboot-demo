package com.demo.app.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用安全白名单等配置。
 */
@ConfigurationProperties(prefix = "demo.app.security")
public class AppSecurityProperties {

    private List<String> permitAll = new ArrayList<>(List.of(
            "/",
            "/login.html",
            "/admin.html",
            "/index.html",
            "/css/**",
            "/js/**",
            "/img/**",
            "/favicon.ico",
            "/auth/api/auth/login",
            "/auth/v3/api-docs/**",
            "/auth/swagger-ui/**",
            "/auth/doc.html",
            "/system/v3/api-docs/**",
            "/system/swagger-ui/**",
            "/system/doc.html",
            "/business/v3/api-docs/**",
            "/business/swagger-ui/**",
            "/business/doc.html",
            "/doc.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/actuator/health"
    ));

    public List<String> getPermitAll() {
        return permitAll;
    }

    public void setPermitAll(List<String> permitAll) {
        this.permitAll = permitAll;
    }
}
