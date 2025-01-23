package inha.jungseokapp.jwt;

import inha.jungseokapp.domain.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey = "vmfhaltmskdlstkfkdgodyroqkfwkdbalroqkfwkdbalaaaaaaaaaaaaaaaabbbbb";

    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    private final long validityInMilliseconds  = 3600000; //  이것도 정해야됨 ->1시간?

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String userId, String role){
        //사용자 id, 역할 정보를 토큰에 담는 과정 (바디 영역)
        Claims claims = Jwts.claims().setSubject(userId); //사용자 id 저장
        claims.put("role", role); //사용자 역할 저장

        Date now = new Date(); //현재 시간
        Date validity = new Date(now.getTime() + validityInMilliseconds); //현재시간 ~ 토큰 만료 시간(1시간 후)

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256) // HS256으로 변경
                .compact();
    }

    public String getUserId(String token){
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes())) // HS256용 키 설정
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token validation failed: " + e.getMessage());
            e.printStackTrace();

            return false;
        }
    }

}
