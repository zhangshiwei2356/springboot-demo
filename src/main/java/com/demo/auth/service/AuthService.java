package com.demo.auth.service;

import com.demo.auth.dto.LoginRequest;
import com.demo.auth.vo.LoginVO;

/**
 * 认证服务接口。
 */
public interface AuthService {

    LoginVO login(LoginRequest request);
}
