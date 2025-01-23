package inha.jungseokapp.security;

import inha.jungseokapp.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private ObjectMapper objectMapper;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = new ObjectMapper();
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            // LoginDTO로 요청 데이터 파싱
            LoginDTO loginDTO = objectMapper.readValue(request.getInputStream(), LoginDTO.class);

            // 인증 토큰 생성
            CustomAuthenticationToken token = new CustomAuthenticationToken(
                    loginDTO.getUsername(),
                    loginDTO.getPassword()
            );

            // Provider에게 인증 위임
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse authentication request", e);
        }
    }
}
