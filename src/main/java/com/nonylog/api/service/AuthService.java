package com.nonylog.api.service;

import com.nonylog.api.domain.User;
import com.nonylog.api.repository.UserRepository;
import com.nonylog.api.request.SignUp;
import com.nonylog.global.crypto.PasswordEncoder;
import com.nonylog.global.exception.AlreadyExistsEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signUp(SignUp signup) {

        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        PasswordEncoder encoder = new PasswordEncoder();
        String encryptedPassword = encoder.encrypt(signup.getPassword());

        var user = User.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();

        userRepository.save(user);
    }
}
