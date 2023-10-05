package project.shop.oauth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/*
 * 각 소셜에서 받아오는 데이터가 다르므로 받아온 사용자 데이터를 담는 객체
 */
@Getter
@Builder
public class OAuthAttributes {

    private String nameAttributeKey; // OAuth 로그인 진행시 키가 되는 필드 값
    private String email;
    private String nickname;

    // 소셜에 맞는 메서도를 호출하여 OAuthAttributes 객체 생성 후 반환
    // registrationId: 소셜 아이디
    // userNameAttributeName: OAuth 로그인 시 키(PK)가 되는 값
    // attributes : OAuth 서비스의 유저 정보들
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){

        // kakao
        if(registrationId.equals("kakao")){
            return ofKakao(userNameAttributeName, attributes);
        } else {
            return null;
        }
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .email((String) kakaoAccount.get("email"))
                .nickname((String) kakaoProfile.get("nickname"))
                .build();
    }
}
