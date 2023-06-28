package com.nonylog.global.config;

import com.nonylog.api.domain.Session;
import com.nonylog.api.repository.SessionRepository;
import com.nonylog.global.config.data.UserSession;
import com.nonylog.global.exception.Unauthorized;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class AuthResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (servletRequest == null) {
            log.error("servletRequest null");
            throw new Unauthorized();
        }

        Cookie[] cookies = servletRequest.getCookies();

        if (cookies.length == 0) {
            log.error("쿠키가 존재하지 않습니다.");
            throw new Unauthorized();
        }

        String accessToken = cookies[0].getValue();

        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(Unauthorized::new);

        return new UserSession(session.getUser().getId());
    }
}
