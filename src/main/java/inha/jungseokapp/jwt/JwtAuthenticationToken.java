package inha.jungseokapp.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true); // 인증 여부 설정
    }

    @Override
    public Object getCredentials() {
        return null; // JWT는 credentials가 필요 없다고 함....?
    }

    @Override
    public Object getPrincipal() {
        return principal; // 사용자 ID 또는 사용자 정보 반환
    }
}
