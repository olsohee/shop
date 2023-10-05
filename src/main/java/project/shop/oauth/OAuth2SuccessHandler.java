package project.shop.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import project.shop.jwt.JwtTokenDto;
import project.shop.jwt.JwtTokenUtils;
import project.shop.security.CustomUserDetails;
import project.shop.security.JpaUserDetailsService;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JpaUserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2SuccessHandler.onAuthenticationSuccess() 실행");

        // OAuth2UserService에서 반환한 DefaultOAuth2User가 저장된다.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // db에 저장된 회원 정보를 토대로 토큰 생성
        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        String email = (String)kakaoAccount.get("email");

        // 회원 조회
        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        // 토큰 발급
        JwtTokenDto tokenDto = jwtTokenUtils.generateToken(user);

        // 파라미터로 토큰을 전달하면서 리다이렉트
        String redirectUrl = String.format("http://localhost:8080/login/kakao?access_token=%s&refresh_token=%s",
                tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
