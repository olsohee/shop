package project.shop.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.shop.entity.user.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.jwt.JwtTokenDto;
import project.shop.jwt.JwtTokenUtils;
import project.shop.repository.UserRepository;
import project.shop.security.CustomUserDetails;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;

    /*
     * OAuth 통신 성공 후 호출되는 메서드
     * // TODO: db 조회 후 사용자 정보 바탕으로 토큰 생성? 전달받은 데이터에서 email을 기반을 토큰 생성?
     * : 전달받은 사용자 정보를 바탕으로 토큰 생성
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2SuccessHandler.onAuthenticationSuccess() 실행");

        // OAuth2UserService에서 반환한 CustomOAuth2User 추출
        CustomOAuth2User oAuth2User= (CustomOAuth2User) authentication.getPrincipal();

        // DB에서 사용자 조회
        User user = userRepository.findByEmail(oAuth2User.getEmail()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        CustomUserDetails customUserDetails = CustomUserDetails.createCustomUserDetails(user);

        // 토큰 발급
        JwtTokenDto tokenDto = jwtTokenUtils.generateToken(customUserDetails);

        // 파라미터로 토큰을 전달하면서 리다이렉트
        String redirectUrl = String.format("http://localhost:8080/oauth/login?access_token=%s&refresh_token=%s",
                tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
