package project.shop.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.shop.entity.user.Authority;
import project.shop.entity.user.User;
import project.shop.repository.UserRepository;

import java.util.Collections;
import java.util.Map;

/*
 * 각 소셜에서 받아온 데이터를 OAuthAttributes로 변환 후, DB에 사용자 정보 저장
 * OAuth2User를 반환하며 OAuth2SuccessHandler가 실행됨
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("CustomOAuth2UserService.loadUser() 실행");

        // 사용자 정보 조회
        OAuth2User oAuth2User = super.loadUser(userRequest); // 여기서 예외 발생

        // (1) registrationId
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // (2) userNameAttributeName: OAuth 로그인 시 키(PK)가 되는 값
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        log.info("userNameAttributeName={}", userNameAttributeName);

        // (3) attributes: 사용자 정보를 담은 Json 객체
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 정제된 사용자 정보를 담는 OAuthAttributes 생성
        OAuthAttributes extractAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);

        // extractAttributes를 토대로 사용자 생성 후 DB 저장
        saveUser(extractAttributes);

        // 사용자 정보를 토대로 CustomOAuth2User 객체 생성 후 반환
        return new CustomOAuth2User(Collections.singleton(new SimpleGrantedAuthority("USER")),
                extractAttributes.getAttributes(), extractAttributes.getNameAttributeKey(),
                extractAttributes.getEmail(), extractAttributes.getName());
    }

    private void saveUser(OAuthAttributes extractAttributes) {

        // 이미 존재하는 이메일이면 DB 저장 X, 새로운 이메일인 경우에만 DB 저장 O
        if(userRepository.findByEmail(extractAttributes.getEmail()).isEmpty()) {
            User user = User.createUser(extractAttributes.getName(), extractAttributes.getEmail(),
                    null, null, Authority.ROLE_USER);
            userRepository.save(user);
        }
    }
}
