package inha.jungseokapp.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws java.io.IOException, jakarta.servlet.ServletException {

        try {
            System.out.println("JwtAuthenticationFilter is called.");

            System.out.println("Request URI: " + request.getRequestURI()); // 요청 URI 확인
            String token = resolveToken(request);
            System.out.println("Resolved Token: " + token); // 읽은 토큰 확인

            if (token != null && jwtTokenProvider.validateToken(token)) {
                String userId = jwtTokenProvider.getUserId(token);
                System.out.println("Authenticated User ID: " + userId); // 유효한 사용자 확인

                SecurityContextHolder.getContext().setAuthentication(
                        new JwtAuthenticationToken(userId, null, null)
                );
            } else {
                System.out.println("Invalid or Missing Token");
            }

            filterChain.doFilter(request, response); //필터 체인 통과!


        }catch(Exception e){
            System.err.println("Exception in JwtAuthenticationFilter: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        System.out.println("Authorization Header: " + bearerToken); // 추가 디버깅 로그

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
