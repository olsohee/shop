package project.shop.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.shop.entity.Authority;
import project.shop.entity.Product;
import project.shop.entity.User;
import project.shop.repository.ProductRepository;
import project.shop.repository.UserRepository;
import project.shop.service.ProductService;

@Component
@RequiredArgsConstructor
public class UserInit {

    private final InitUser initUser;
    private final InitProduct initProduct;

    @PostConstruct
    public void init() {

        initUser.init();
        initProduct.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitUser {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        public void init() {

            // 관리자
            User user1 = User.createUser("관리자", "admin@naver.com",
                    passwordEncoder.encode("1111"), "01012345678", Authority.ROLE_ADMIN);
            userRepository.save(user1);

            // 회원
            User user2 = User.createUser("관리자", "min@naver.com",
                    passwordEncoder.encode("1111"), "01012345678", Authority.ROLE_USER);
            userRepository.save(user2);
        }
    }

    @Component
    @RequiredArgsConstructor
    static class InitProduct {

        private final ProductRepository productRepository;

        public void init() {

            Product product1 = Product.createProduct("티셔츠", 80000, 15);
            productRepository.save(product1);

            Product product2 = Product.createProduct("트랙팬츠", 150000, 10);
            productRepository.save(product2);

            Product product3 = Product.createProduct("볼캡", 45000, 25);
            productRepository.save(product3);
        }
    }
}
