package team10.group_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team10.group_app.dto.TokenDTO;
import team10.group_app.dto.UserSignupDTO;
import team10.group_app.service.TokenService;
import team10.group_app.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenDTO> signup(@RequestBody UserSignupDTO userSignupDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.signupNewUser(userSignupDTO));
    }

     @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(Authentication authentication) {
        TokenDTO tokenDTO = userService.loginUser(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
    }
}
