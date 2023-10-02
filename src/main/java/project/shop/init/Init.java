package project.shop.init;

import jakarta.annotation.PostConstruct;
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

@Component
@RequiredArgsConstructor
public class Init {

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
        private final CartService cartService;

        public void init() {

            // 관리자
            User user1 = User.createUser("관리자", "admin@naver.com",
                    passwordEncoder.encode("1111"), "01012345678",
                    Authority.ROLE_ADMIN);
            userRepository.save(user1);

            // 회원
            User user2 = User.createUser("min", "min@naver.com",
                    passwordEncoder.encode("1111"), "01012345678",
                    Authority.ROLE_USER);
            userRepository.save(user2);
        }
    }

    @Component
    @RequiredArgsConstructor
    static class InitProduct {

        private final ProductRepository productRepository;

        public void init() {

            Product product1 = Product.createProduct("삼겹살", 15000, 15, ProductCategory.PORK);
            productRepository.save(product1);

            Product product2 = Product.createProduct("생과일 자몽 주스", 8000, 10, ProductCategory.JUICE);
            productRepository.save(product2);

            Product product3 = Product.createProduct("갈치", 9900, 25, ProductCategory.FISH);
            productRepository.save(product3);
        }
    }
}
