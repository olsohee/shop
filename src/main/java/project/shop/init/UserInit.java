package project.shop.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.shop.entity.Authority;
import project.shop.entity.User;
import project.shop.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserInit {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUser() {

        User user = User.createUser("관리자", "admin@naver.com",
                passwordEncoder.encode("1111"), "01012345678", Authority.ROLE_ADMIN);
        userRepository.save(user);
    }
}
