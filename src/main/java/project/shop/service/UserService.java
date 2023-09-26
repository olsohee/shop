package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public Long join(User user) {

        // 이미 가입된 email인 경우 예외 발생
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // 회원가입
        userRepository.save(user);
        return user.getId();
    }

    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    @Transactional
    public JwtTokenDto login(String email, String password) {

        // 가입된 email인지 확인
        User user = findByEmail(email);

        // password가 올바른지 확인
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INCORRECT_PASSWORD);
        }

        // JWT 생성
        JwtTokenDto jwtTokenDto = jwtTokenUtils.generateToken(CustomUserDetails.createCustomUserDetails(user));

        // Refresh Token DB에 저장
        refreshTokenService.save(new RefreshToken(email, jwtTokenDto.getRefreshToken()));

        return jwtTokenUtils.generateToken(CustomUserDetails.createCustomUserDetails(user));
    }
}
