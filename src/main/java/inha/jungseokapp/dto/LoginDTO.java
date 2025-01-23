package inha.jungseokapp.dto;
import inha.jungseokapp.domain.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String username;
    private String password;
    private Role role;
}
