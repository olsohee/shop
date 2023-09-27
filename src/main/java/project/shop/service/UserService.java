package project.shop.service;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.dto.user.JoinRequest;
import project.shop.dto.user.JoinResponse;
import project.shop.dto.user.LoginRequest;
import project.shop.entity.Authority;
import project.shop.entity.RefreshToken;
import project.shop.entity.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.jwt.JwtTokenDto;
import project.shop.jwt.JwtTokenUtils;
import project.shop.repository.UserRepository;
import project.shop.security.CustomUserDetails;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    @Transactional
    public JoinResponse join(JoinRequest dto) {

        // 이미 가입된 email인 경우 예외 발생
        if(userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 회원가입
        User user = User.createUser(dto.getUsername(), dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()), dto.getPhoneNumber(),
                Authority.ROLE_USER);

        userRepository.save(user);

        // 응답
        return new JoinResponse(user.getUsername(), user.getEmail(), user.getPhoneNumber());
    }

    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    @Transactional
    public JwtTokenDto createToken(LoginRequest dto) {

        // 가입된 email인지 확인
        User user = findByEmail(dto.getEmail());

        // password가 올바른지 확인
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }

        // JWT 생성
        JwtTokenDto jwtTokenDto = jwtTokenUtils.generateToken(CustomUserDetails.createCustomUserDetails(user));

        // Refresh Token DB에 저장
        refreshTokenService.save(new RefreshToken(dto.getEmail(), jwtTokenDto.getRefreshToken()));

        return jwtTokenUtils.generateToken(CustomUserDetails.createCustomUserDetails(user));
    }

    @Transactional
    public JwtTokenDto recreateToken(HttpServletRequest request) {

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
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        JwtTokenDto jwtTokenDto = jwtTokenUtils.generateToken(CustomUserDetails.createCustomUserDetails(user));

        // DB에 새로운 Refresh Token 저장
        refreshTokenService.save(new RefreshToken(email, refreshToken));

        return jwtTokenDto;
    }


}
