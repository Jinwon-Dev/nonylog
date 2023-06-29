package com.nonylog.api.service;

import com.nonylog.api.domain.User;
import com.nonylog.api.repository.UserRepository;
import com.nonylog.api.request.SignUp;
import com.nonylog.global.exception.AlreadyExistsEmailException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotEquals("1234", user.getPassword());
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
}