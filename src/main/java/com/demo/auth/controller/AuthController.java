package com.demo.auth.controller;

import com.demo.auth.dto.LoginRequest;
import com.demo.auth.service.AuthService;
import com.demo.auth.vo.LoginVO;
import com.demo.common.base.BaseController;
import com.demo.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证")
public class AuthController extends BaseController<LoginRequest, LoginVO> {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "账号密码登录（演示：密码固定123456）")
    public Result<LoginVO> login(@Valid @RequestBody LoginRequest dto) {
        return ok(authService.login(dto));
    }
}
