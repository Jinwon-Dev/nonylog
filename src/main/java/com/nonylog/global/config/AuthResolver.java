package com.nonylog.global.config;

import com.nonylog.global.config.data.UserSession;
import com.nonylog.global.exception.Unauthorized;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String accessToken = webRequest.getHeader("Authorization");

        if (accessToken == null || accessToken.equals("")) {
            throw new Unauthorized();
        }

        // TODO: 데이터베이스 사용자 확인 작업

        return new UserSession(1L);
    }
}
