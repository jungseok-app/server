package inha.jungseokapp.controller;
import inha.jungseokapp.domain.UserEntity;
import inha.jungseokapp.dto.LoginDTO;
import inha.jungseokapp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            // 로그
            System.out.println("로그인 시도 -> 유저이름: " + loginDTO.getUsername());

            Optional<UserEntity> user = userRepository.findByUsername(loginDTO.getUsername());
            if (user.isEmpty()) {
                return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다");
            }

            if (passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
                return ResponseEntity.ok().body("로그인 성공");
            } else {
                return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("로그인 실패 " + e.getMessage());
        }
    }
}
