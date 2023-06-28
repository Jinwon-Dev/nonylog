package com.nonylog.api.service;

import com.nonylog.api.domain.Session;
import com.nonylog.api.domain.User;
import com.nonylog.api.repository.UserRepository;
import com.nonylog.api.request.Login;
import com.nonylog.api.request.SignUp;
import com.nonylog.global.exception.AlreadyExistsEmailException;
import com.nonylog.global.exception.InvalidSignInInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signIn(Login login) {

        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSignInInformation::new);
        Session session = user.addSession();

        return user.getId();
    }

    public void signUp(SignUp signup) {

        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        SCryptPasswordEncoder encoder = new SCryptPasswordEncoder(16, 8, 1, 32, 64);
        String encryptedPassword = encoder.encode(signup.getPassword());

        var user = User.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();

        userRepository.save(user);
    }
}
