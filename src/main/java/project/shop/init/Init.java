package project.shop.init;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.shop.entity.product.ProductCategory;
import project.shop.entity.user.Authority;
import project.shop.entity.product.Product;
import project.shop.entity.user.User;
import project.shop.repository.ProductRepository;
import project.shop.repository.UserRepository;
import project.shop.service.CartService;

@Transactional
@Component
@RequiredArgsConstructor
public class Init {

    private final InitUser initUser;

    @PostConstruct
    public void init() {

        initUser.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitUser {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public void init() {

            // 관리자
            User user1 = User.createUser("관리자", "admin@naver.com",
                    passwordEncoder.encode("1111"), "01012345678",
                    Authority.ROLE_ADMIN);
            userRepository.save(user1);

            // 회원
            User user2 = User.createUser("kim", "kim@naver.com",
                    passwordEncoder.encode("1111"), "01012345678",
                    Authority.ROLE_USER);
            userRepository.save(user2);

            User user3 = User.createUser("lee", "lee@daum.net",
                    passwordEncoder.encode("2222"), "01011112222",
                    Authority.ROLE_USER);
            userRepository.save(user3);

            User user4 = User.createUser("park", "park@naver.com",
                    passwordEncoder.encode("3333"), "01044443333",
                    Authority.ROLE_USER);
            userRepository.save(user4);

            User user5 = User.createUser("min", "min@naver.com",
                    passwordEncoder.encode("4444"), "01011221122",
                    Authority.ROLE_USER);
            userRepository.save(user5);
        }
    }
}
