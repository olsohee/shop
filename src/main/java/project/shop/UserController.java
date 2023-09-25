package project.shop;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.JoinRequest;
import project.shop.dto.JoinResponse;
import project.shop.entity.RefreshToken;
import project.shop.jwt.JwtTokenDto;
import project.shop.dto.LoginRequest;
import project.shop.entity.User;
import project.shop.jwt.JwtTokenUtils;
import project.shop.repository.RefreshTokenRepository;
import project.shop.security.CustomUserDetails;
import project.shop.service.RefreshTokenService;
import project.shop.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenService refreshTokenService;

    /**
     * [POST] /join
     * 회원가입
     */
    @PostMapping("/join")
    public JoinResponse join(@RequestBody JoinRequest joinRequest) {

        // 가입
        User user = User.createUser(joinRequest.getUsername(), joinRequest.getEmail(),
                passwordEncoder.encode(joinRequest.getPassword()), joinRequest.getPhoneNumber());
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
    public JwtTokenDto login(@RequestBody LoginRequest loginRequest) {

        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    /**
     * [POST] /recreation
     * Access Token 재발급
     */
    @PostMapping("/recreation")
    public JwtTokenDto recreationAccessToken(HttpServletRequest request) {

        // 요청 헤더에서 Refresh Token 추출
        String refreshToken = jwtTokenUtils.getTokenFromRequest(request);

        // Refresh Token 유효성 검사
        jwtTokenUtils.validateToken(refreshToken);

        // DB에 저장된 Refresh Token과 일치하는지 확인
        String email = jwtTokenUtils.getEmailFromHeader(request);
        User user = userService.findByEmail(email);

        if(!jwtTokenUtils.compareRefreshToken(user, refreshToken)) {
            // 일치하지 않으면 예외 발생
            throw new JwtException("잘못된 Refresh Token입니다");
        }

        // 일치하면 Access Token, Refresh Token 재발급, DB에 새로운 Refresh Token 저장
        JwtTokenDto jwtTokenDto = jwtTokenUtils.generateToken(CustomUserDetails.createCustomUserDetails(user));
        refreshTokenService.changeRefreshToken(user, jwtTokenDto.getRefreshToken());
        return jwtTokenDto;
    }
}