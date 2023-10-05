package project.shop.oauth;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/*
 * 각 소셜에서 받아오는 데이터가 다르므로 받아온 사용자 데이터를 담는 객체
 */
@Getter
@Builder
@Slf4j
public class OAuthAttributes {

    private Map<String, Object> attributes; // OAuth를 통해 받은 사용자 데이터
    private String nameAttributeKey;
    private String email;
    private String name;

    /*
     * 소셜에 맞는 메서도를 호출하여 OAuthAttributes 객체 생성 후 반환
     * - registrationId: 소셜 아이디
     * - userNameAttributeName: OAuth 로그인 시 키가 되는 값
     * - attributes : OAuth 서비스의 유저 정보들
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){

        if(registrationId.equals("kakao")){
            return ofKakao(userNameAttributeName, attributes);
        }

        if(registrationId.equals("google")) {
            return ofGoogle(userNameAttributeName, attributes);
        }

        return null;
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {

        log.info("attributes={}", attributes.toString());

        Map<String, Object> account = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .email((String) account.get("email"))
                .name((String) profile.get("nickname"))
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        log.info("attributes={}", attributes.toString());

        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .build();
    }
}
