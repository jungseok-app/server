package com.example.libraryinha.dto;


import com.example.libraryinha.domain.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    //유저 정보 가져오기
    private final UserEntity user;
    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }

    @Override
    //사용자 권한 반환 (접근 권한 제한)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().name();
            }
        });
        return collect;
    }


    //엔티티에서 유저 정보 가져오기
    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUsername();
    }


    @Override
    //계정 만료 여부
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    //계정 잠금 여부
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    //비밀번호 만료 여부
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    //계정 활성화 여부
    public boolean isEnabled() {
        return true;
    }
}
