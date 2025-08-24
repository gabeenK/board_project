package com.ukm.ssgb.service;

import com.ukm.ssgb.exception.ValidationException;
import com.ukm.ssgb.model.User;
import com.ukm.ssgb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() -> new ValidationException("아이디 또는 비밀번호가 틀렸습니다."));

        String encodedPassword = user.getPassword();
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ValidationException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        user.login();

        return user;
    }
}
