package com.cloud.app.filter;

import com.cloud.app.security.AppSecurityProperties;
import com.cloud.common.constant.Constants;
import com.cloud.common.constant.MdcKeys;
import com.cloud.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 全局 JWT 校验，解析 UID 写入 {@link Constants#HEADER_LOGIN_USER_ID} 供业务使用。
 */
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final AntPathMatcher MATCHER = new AntPathMatcher();

    private final JwtUtil jwtUtil;
    private final AppSecurityProperties securityProperties;

    public JwtAuthFilter(JwtUtil jwtUtil, AppSecurityProperties securityProperties) {
        this.jwtUtil = jwtUtil;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (isWhitelisted(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        String raw = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(raw)) {
            writeUnauthorized(response, "缺少凭证");
            return;
        }
        raw = raw.trim();
        if (!raw.regionMatches(true, 0, "Bearer ", 0, "Bearer ".length())) {
            writeUnauthorized(response, "凭证格式错误");
            return;
        }
        String token = raw.substring("Bearer ".length()).trim();
        try {
            Long uid = jwtUtil.parseUid(token);
            MDC.put(MdcKeys.USER_ID, String.valueOf(uid));
            HttpServletRequest wrapped = new LoginUserIdRequestWrapper(request, uid);
            try {
                filterChain.doFilter(wrapped, response);
            } finally {
                MDC.remove(MdcKeys.USER_ID);
            }
        } catch (Exception ex) {
            writeUnauthorized(response, ex.getMessage());
        }
    }

    private boolean isWhitelisted(String path) {
        List<String> permits = securityProperties.getPermitAll();
        if (permits == null) {
            return false;
        }
        for (String p : permits) {
            if (MATCHER.match(p, path)) {
                return true;
            }
        }
        return false;
    }

    private static void writeUnauthorized(HttpServletResponse resp, String msg) throws IOException {
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String body = "{\"code\":401,\"message\":\"" + msg.replace("\"", "'") + "\",\"data\":null}";
        resp.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
    }

    private static final class LoginUserIdRequestWrapper extends jakarta.servlet.http.HttpServletRequestWrapper {

        private final Long uid;

        private LoginUserIdRequestWrapper(HttpServletRequest request, Long uid) {
            super(request);
            this.uid = uid;
        }

        @Override
        public String getHeader(String name) {
            if (Constants.HEADER_LOGIN_USER_ID.equalsIgnoreCase(name)) {
                return String.valueOf(uid);
            }
            return super.getHeader(name);
        }
    }
}
