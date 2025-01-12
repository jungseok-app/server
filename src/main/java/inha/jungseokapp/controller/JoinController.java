package com.example.libraryinha.controller;

import com.example.libraryinha.dto.JoinDTO;
import com.example.libraryinha.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class JoinController {
    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinProcess(@RequestBody JoinDTO joinDTO) {

        // 받은 데이터 로깅
        System.out.println("유저 이름: " + joinDTO.getUsername());
        System.out.println("비밀번호: " + joinDTO.getPassword());
        System.out.println("이메일: " + joinDTO.getEmail());

        joinService.joinProcess(joinDTO);
        return ResponseEntity.ok().build();
    }
}
