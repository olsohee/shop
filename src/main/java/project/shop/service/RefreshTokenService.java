package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.RefreshToken;
import project.shop.entity.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.RefreshTokenRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void changeRefreshToken(User user, String refreshToken) {

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUser(user).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        refreshTokenEntity.setRefreshToken(refreshToken);
    }
}
