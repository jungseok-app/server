package com.example.libraryinha.service;

import com.example.libraryinha.domain.Role;
import com.example.libraryinha.domain.UserEntity;
import com.example.libraryinha.dto.JoinDTO;
import com.example.libraryinha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public void joinProcess(JoinDTO joinDTO){

        //이메일과 username 중복 여부
        if (userRepository.existsByEmail(joinDTO.getEmail())){
            throw new IllegalStateException("Email already exists.");
        }
        if (userRepository.existsByUsername(joinDTO.getUsername())){
            throw new IllegalStateException("Username already exists.");
        }


        //계정 생성
        UserEntity userdata = new UserEntity();
        userdata.setUsername(joinDTO.getUsername());
        userdata.setEmail(joinDTO.getEmail());
        userdata.setRole(Role.USER);

        //비밀번호 암호화
        userdata.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));

        //저장
        userRepository.save(userdata);
    }

}
