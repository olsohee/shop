package project.shop.oauth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

/*
 * DefaultOAuth2User를 상속받고, email과 name을 추가로 가짐
 */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private String email;
    private String name;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey,
                            String email, String name) {

        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.name = name;
    }
}
