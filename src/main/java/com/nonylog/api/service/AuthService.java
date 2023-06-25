package com.nonylog.api.service;

import com.nonylog.api.domain.Session;
import com.nonylog.api.domain.User;
import com.nonylog.api.repository.UserRepository;
import com.nonylog.api.request.Login;
import com.nonylog.global.exception.InvalidSignInInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public String signIn(Login login) {

        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSignInInformation::new);
        Session session = user.addSession();

        return session.getAccessToken();
    }
}
