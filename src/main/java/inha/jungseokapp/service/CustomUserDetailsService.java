package inha.jungseokapp.service;

import inha.jungseokapp.domain.UserEntity;
import inha.jungseokapp.dto.CustomUserDetails;
import inha.jungseokapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    //리포지토리 주입
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 인증 정보 로드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 사용자 조회 (Optional로 반환)
        Optional<UserEntity> userData = userRepository.findByUsername(username);

        // 사용자 존재하지 않을 때 예외 처리
        if (userData.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        // CustomUserDetails 객체 반환
        return new CustomUserDetails(userData.get());
    }
}
