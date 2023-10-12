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

    @Component
    @RequiredArgsConstructor
    static class InitProduct {

        private final ProductRepository productRepository;

        public void init() {

            Product product1 = Product.createProduct("고구마", 15000, 50, ProductCategory.SWEET_POTATO);
            Product product2 = Product.createProduct("감자", 15000, 50, ProductCategory.SWEET_POTATO);
            Product product3 = Product.createProduct("양파", 9000, 40, ProductCategory.ONION);
            Product product4 = Product.createProduct("마늘", 7000, 50, ProductCategory.ONION);
            Product product5 = Product.createProduct("냉동 야채 믹스", 9000, 20, ProductCategory.FROZEN_VEGETABLE);

            Product product6 = Product.createProduct("무농약 사과", 12000, 20, ProductCategory.DOMESTIC_FRUIT);
            Product product7 = Product.createProduct("감", 10000, 20, ProductCategory.DOMESTIC_FRUIT);
            Product product8 = Product.createProduct("용과", 14000, 33, ProductCategory.IMPORT_FRUIT);
            Product product9 = Product.createProduct("망고", 14000, 20, ProductCategory.IMPORT_FRUIT);
            Product product10 = Product.createProduct("냉동 베리 믹스", 10000, 20, ProductCategory.FROZEN_FRUIT);
            Product product11 = Product.createProduct("냉동 블루베리", 9000, 20, ProductCategory.FROZEN_FRUIT);

            Product product12 = Product.createProduct("고등어", 9000, 40, ProductCategory.FISH);
            Product product13 = Product.createProduct("오징어", 17000, 50, ProductCategory.SQUID);
            Product product14 = Product.createProduct("손질 낙지", 18000, 20, ProductCategory.SQUID);
            Product product15 = Product.createProduct("생 새우", 18000, 30, ProductCategory.SHRIMP);
            Product product16 = Product.createProduct("냉동 새우", 13000, 80, ProductCategory.SHRIMP);

            Product product17 = Product.createProduct("한우", 22000, 20, ProductCategory.BEEF);
            Product product18 = Product.createProduct("목살", 16000, 20, ProductCategory.PORK);
            Product product19 = Product.createProduct("삼겹살", 16000, 24, ProductCategory.PORK);
            Product product20 = Product.createProduct("닭가슴살", 9000, 90, ProductCategory.CHICKEN);
            Product product21 = Product.createProduct("계란", 8000, 80, ProductCategory.EGG);
            Product product22 = Product.createProduct("난백", 7000, 40, ProductCategory.EGG);

            Product product23 = Product.createProduct("생수", 800, 1000, ProductCategory.WATER);
            Product product24 = Product.createProduct("제로콜라", 1000, 100, ProductCategory.JUICE);
            Product product25 = Product.createProduct("자몽 주스", 2000, 100, ProductCategory.JUICE);
            Product product26 = Product.createProduct("당근 주스", 1800, 100, ProductCategory.JUICE);
            Product product27 = Product.createProduct("우유", 1700, 100, ProductCategory.MILK);
            Product product28 = Product.createProduct("그릭 요거트", 2500, 88, ProductCategory.MILK);
            Product product29 = Product.createProduct("페퍼민트 티", 2500, 120, ProductCategory.TEA);
            Product product30 = Product.createProduct("녹차", 1800, 150, ProductCategory.TEA);
            Product product31 = Product.createProduct("커피", 1800, 80, ProductCategory.DRINK);

            Product product32 = Product.createProduct("치아바타", 9000, 40, ProductCategory.BREAD);
            Product product33 = Product.createProduct("체다치즈", 2500, 44, ProductCategory.CHEESE);
            Product product34 = Product.createProduct("리코타치즈", 3500, 50, ProductCategory.CHEESE);
            Product product35 = Product.createProduct("땅콩버터", 4000, 20, ProductCategory.JAM);

            Product product36 = Product.createProduct("프로틴바", 2500, 50, ProductCategory.COOKIE);
            Product product37 = Product.createProduct("다크초콜릿", 1500, 70, ProductCategory.CHOCOLATE);
            Product product38 = Product.createProduct("밴엔제리 아이스크림", 5500, 13, ProductCategory.ICE_CREAM);

            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
            productRepository.save(product4);
            productRepository.save(product5);
            productRepository.save(product6);
            productRepository.save(product7);
            productRepository.save(product8);
            productRepository.save(product9);
            productRepository.save(product10);
            productRepository.save(product11);
            productRepository.save(product12);
            productRepository.save(product13);
            productRepository.save(product14);
            productRepository.save(product15);
            productRepository.save(product16);
            productRepository.save(product17);
            productRepository.save(product18);
            productRepository.save(product19);
            productRepository.save(product20);
            productRepository.save(product21);
            productRepository.save(product22);
            productRepository.save(product23);
            productRepository.save(product24);
            productRepository.save(product25);
            productRepository.save(product26);
            productRepository.save(product27);
            productRepository.save(product28);
            productRepository.save(product29);
            productRepository.save(product30);
            productRepository.save(product31);
            productRepository.save(product32);
            productRepository.save(product33);
            productRepository.save(product34);
            productRepository.save(product35);
            productRepository.save(product36);
            productRepository.save(product37);
            productRepository.save(product38);
        }
    }
}
