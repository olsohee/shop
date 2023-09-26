package project.shop.controller;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.user.JoinRequest;
import project.shop.dto.user.JoinResponse;
import project.shop.entity.Authority;
import project.shop.entity.Cart;
import project.shop.entity.RefreshToken;
import project.shop.jwt.JwtTokenDto;
import project.shop.dto.user.LoginRequest;
import project.shop.entity.User;
import project.shop.jwt.JwtTokenUtils;
import project.shop.security.CustomUserDetails;
import project.shop.service.CartService;
import project.shop.service.RefreshTokenService;
import project.shop.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenService refreshTokenService;

    /**
     * [POST] /join
     * 회원가입
     */
    @PostMapping("/join")
    public JoinResponse join(@RequestBody JoinRequest dto) {

        // 가입
        Cart cart = Cart.createCart();
        cartService.save(cart);

        User user = User.createUser(dto.getUsername(), dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()), dto.getPhoneNumber(),
                Authority.ROLE_USER, cart);
        Long id = userService.join(user);

        // 조회
        User findUser = userService.findById(id);
        return new JoinResponse(findUser.getUsername(), findUser.getEmail(), findUser.getPhoneNumber());
    }

    /**
     * [POST] /login
     * 로그인
     */
    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody LoginRequest dto) {

        return userService.login(dto.getEmail(), dto.getPassword());
    }

    /**
     * [POST] /recreation
     * Access Token 재발급
     */
    @PostMapping("/recreation")
    public JwtTokenDto recreationAccessToken(HttpServletRequest request) {

        // 요청 헤더에서 Refresh Token 추출
        String refreshToken = jwtTokenUtils.getTokenFromRequest(request);

        // 1. Refresh Token 유효성 검사
        jwtTokenUtils.validateToken(refreshToken);

        // 2. DB에 저장된 Refresh Token과 일치하는지 확인
        String email = jwtTokenUtils.getEmailFromHeader(request);

        if(!jwtTokenUtils.compareRefreshToken(email, refreshToken)) {
            // 일치하지 않으면 예외 발생
            throw new JwtException("잘못된 Refresh Token입니다");
        }

        // Access Token, Refresh Token 재발급
        User user = userService.findByEmail(email);
        JwtTokenDto jwtTokenDto = jwtTokenUtils.generateToken(CustomUserDetails.createCustomUserDetails(user));

        // DB에 새로운 Refresh Token 저장
        refreshTokenService.save(new RefreshToken(email, refreshToken));

        return jwtTokenDto;
    }
}