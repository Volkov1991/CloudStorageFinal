package netology.cloudstoragefinal.controller;

import netology.cloudstoragefinal.dto.LoginResponse;
import netology.cloudstoragefinal.dto.UserDto;
import netology.cloudstoragefinal.service.AuthService;
import netology.cloudstoragefinal.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto user) {
        String token = authService.authenticate(UserMapper.mapToUserEntity(user));
        log.info("Successfully logged in: {}", user.getLogin());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
