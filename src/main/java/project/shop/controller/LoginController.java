package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.login.JoinRequest;
import project.shop.dto.login.JoinResponse;
import project.shop.jwt.JwtTokenDto;
import project.shop.dto.login.LoginRequest;
import project.shop.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final UserService userService;

    /**
     * [POST] /join
     * 회원가입
     */
    @PostMapping("/join")
    public JoinResponse join(@RequestBody JoinRequest dto) {

        return userService.join(dto);
    }

    /**
     * [POST] /createToken
     * 로그인
     */
    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginRequest dto) {

        return userService.createToken(dto);
    }

    /**
     * [POST] /recreation
     * Access Token 재발급
     */
    @PostMapping("/recreation")
    public JwtTokenDto recreationAccessToken(HttpServletRequest request) {

        return userService.recreateToken(request);
    }
}