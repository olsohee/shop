package project.shop.oAuth;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/*
 * OAuth 인증 과정에서 받아온 사용자 정보를 담는 객체
 */
@Getter
@Builder
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String nickname;

    //== 생성 메서드 ==//
    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes){

        // kakao
        if(registrationId.equals("kakao")){
            return ofKakao("id", attributes);
        } else {
            return null;
        }
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .email((String) kakaoAccount.get("email"))
                .nickname((String) kakaoProfile.get("nickname"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
}
