package project.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.shop.entity.user.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.UserRepository;

/*
 * Spring Security에서 DB에 저장된 사용자 정보를 가져오는 클래스
 * DB에서 사용자 조회 후 UserDetails로 반환
 */
@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        CustomUserDetails customUserDetails = CustomUserDetails.createCustomUserDetails(user);
        return customUserDetails;
    }
}
