package com.cloud.app.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用安全白名单等配置。
 */
@ConfigurationProperties(prefix = "cloud.app.security")
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
            "/cloud-auth/api/auth/login",
            "/cloud-auth/v3/api-docs/**",
            "/cloud-auth/swagger-ui/**",
            "/cloud-auth/doc.html",
            "/cloud-system/v3/api-docs/**",
            "/cloud-system/swagger-ui/**",
            "/cloud-system/doc.html",
            "/cloud-business/v3/api-docs/**",
            "/cloud-business/swagger-ui/**",
            "/cloud-business/doc.html",
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
