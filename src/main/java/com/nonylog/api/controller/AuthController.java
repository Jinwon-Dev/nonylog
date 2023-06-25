package com.nonylog.api.controller;

import com.nonylog.api.request.Login;
import com.nonylog.api.response.SessionResponse;
import com.nonylog.api.service.AuthService;
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

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {

        String accessToken = authService.signIn(login);
        return new SessionResponse(accessToken);
    }
}
