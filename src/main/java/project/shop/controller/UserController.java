package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.user.JoinRequest;
import project.shop.dto.user.JoinResponse;
import project.shop.jwt.JwtTokenDto;
import project.shop.dto.user.LoginRequest;
import project.shop.jwt.JwtTokenUtils;
import project.shop.service.RefreshTokenService;
import project.shop.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenService refreshTokenService;

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