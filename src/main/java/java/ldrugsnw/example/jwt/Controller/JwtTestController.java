package java.ldrugsnw.example.jwt.Controller;

import org.springframework.web.bind.annotation.*;

import java.ldrugsnw.example.jwt.jwt.JwtTokenProvider;

@RestController
@RequestMapping("/test")
public class JwtTestController {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTestController(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/token")
    public String generateToken(){
        return jwtTokenProvider.createToken("testUser", "ROLE_USER");
    }

    @PostMapping("/validate")
    public String validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        System.out.println("Authorization Header: " + authorizationHeader);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("Token is missing or invalid...");
            return "Token is missing or invalid.";
        }

        String token = authorizationHeader.substring(7);

        // 유효성 검사
        if (jwtTokenProvider.validateToken(token)) {
            String userId = jwtTokenProvider.getUserId(token);
            System.out.println("Valid token for user: " + userId);
            return "Valid Token: " + userId;
        } else {
            System.out.println("Token validation failed.");
            return "Invalid Token";
        }
    }
}

