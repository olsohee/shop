package project.shop.oAuth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import project.shop.entity.user.Authority;
import project.shop.entity.user.User;
import project.shop.repository.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("OAuth2UserService.loadUser() 실행");

        // 사용자 정보 조회
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth 서비스 id(application.yml에 등록한 Provider id)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 사용자 정보를 담는 OAuthAttributes 생성
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, oAuth2User.getAttributes());

        // OAuthAttributes를 토대로 사용자 생성 후 DB 저장
        saveUser(attributes);

        log.info("========{}", attributes.getNameAttributeKey());
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private void saveUser(OAuthAttributes attributes) {

        // 이미 존재하는 이메일이면 DB 저장 X, 새로운 이메일인 경우에만 DB 저장 O
        if(userRepository.findByEmail(attributes.getEmail()).isEmpty()) {
            User user = User.createUser(attributes.getNickname(), attributes.getEmail(),
                    null, null, Authority.ROLE_USER);
            userRepository.save(user);
        }
    }
}
