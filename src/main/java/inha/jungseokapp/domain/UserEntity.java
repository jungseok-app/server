package inha.jungseokapp.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //필수 인자
    private Long id;

    private String username;
    private String password;

    private Role role; //user, admin
    private LocalDateTime createdAt;

    //둘 중 하나 선택
    private String email;
    private String phoneNumber;
}
