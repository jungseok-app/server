package inha.jungseokapp.repository;

import inha.jungseokapp.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //회원 존재 여부
    Boolean existsByUsername(String username);

    //이메일 존재 여부
    Boolean existsByEmail(String email);

    //회원이름으로 회원정보 찾기
    Optional<UserEntity> findByUsername(String username);


    //이메일로 비밀번호 찾기
    Optional<UserEntity> findByEmail(String email);

}
