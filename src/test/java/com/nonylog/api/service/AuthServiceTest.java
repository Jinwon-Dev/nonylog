package com.nonylog.api.service;

import com.nonylog.api.domain.User;
import com.nonylog.api.repository.UserRepository;
import com.nonylog.api.request.Login;
import com.nonylog.api.request.SignUp;
import com.nonylog.global.crypto.ScryptPasswordEncoder;
import com.nonylog.global.exception.AlreadyExistsEmailException;
import com.nonylog.global.exception.InvalidSignInInformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {

        // given
        SignUp signUp = SignUp.builder()
                .password("1234")
                .email("jinony99@gmail.com")
                .name("jinony")
                .build();

        // when
        authService.signUp(signUp);

        // then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("jinony99@gmail.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertEquals("1234", user.getPassword());
        assertEquals("jinony", user.getName());
    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2() {

        // given
        User user = User.builder()
                .email("jinony99@gmail.com")
                .password("1234")
                .name("짱돌맨")
                .build();
        userRepository.save(user);

        SignUp signUp = SignUp.builder()
                .password("1234")
                .email("jinony99@gmail.com")
                .name("jinony")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signUp(signUp));
    }

    @Test
    @DisplayName("로그인 성공")
    void test3() {

        // given
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        String encryptedPassword = encoder.encrypt("1234");

        User user = User.builder()
                .email("jinony99@gmail.com")
                .password(encryptedPassword)
                .name("짱돌맨")
                .build();
        userRepository.save(user);

        Login login = Login.builder()
                .email("jinony99@gmail.com")
                .password("1234")
                .build();

        // when
        Long userId = authService.signIn(login);

        // then
        assertNotNull(userId);
    }

    @Test
    @DisplayName("로그인시 비밀번호 틀림")
    void test4() {

        // given
        SignUp signUp = SignUp.builder()
                .password("1234")
                .email("jinony99@gmail.com")
                .name("jinony")
                .build();
        authService.signUp(signUp);

        Login login = Login.builder()
                .email("jinony99@gmail.com")
                .password("5678")
                .build();

        // expected
        assertThrows(InvalidSignInInformation.class, () -> authService.signIn(login));
    }
}