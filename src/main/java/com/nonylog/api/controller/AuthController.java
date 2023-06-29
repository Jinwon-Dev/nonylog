package com.nonylog.api.controller;

import com.nonylog.api.request.SignUp;
import com.nonylog.api.service.AuthService;
import com.nonylog.global.config.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/signup")
    public void signup(@RequestBody SignUp signup) {
        authService.signUp(signup);
    }
}
