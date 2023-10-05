package project.shop.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.shop.entity.user.Authority;
import project.shop.entity.user.User;

import java.util.ArrayList;
import java.util.Collection;

/*
 * Spring Security에서 사용자 정보를 담는 인터페이스인 UserDeailts를 구현한 클래스
 * UserDeailts에 정의된 필드 외에 프로젝트에 필요한 사용자 정보들을 추가로 정의
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CustomUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private Authority authority;

    public static CustomUserDetails createCustomUserDetails(User user) {

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.id = user.getId();
        customUserDetails.username = user.getUsername();
        customUserDetails.email = user.getEmail();
        customUserDetails.password = user.getPassword();
        customUserDetails.phoneNumber = user.getPhoneNumber();
        customUserDetails.authority = user.getAuthority();

        return customUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority.name());
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
