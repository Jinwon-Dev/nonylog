package com.nonylog.api.service;

import com.nonylog.api.domain.User;
import com.nonylog.api.repository.UserRepository;
import com.nonylog.api.request.Login;
import com.nonylog.api.request.SignUp;
import com.nonylog.global.crypto.PasswordEncoder;
import com.nonylog.global.exception.AlreadyExistsEmailException;
import com.nonylog.global.exception.InvalidSignInInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signIn(Login login) {

        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSignInInformation::new);

        var matches = passwordEncoder.matches(login.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidSignInInformation();
        }

        return user.getId();
    }

    public void signUp(SignUp signup) {

        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        var user = User.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();

        userRepository.save(user);
    }
}
