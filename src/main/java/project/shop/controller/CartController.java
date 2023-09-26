package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.shop.entity.Cart;
import project.shop.entity.CartProduct;
import project.shop.entity.Product;
import project.shop.entity.User;
import project.shop.jwt.JwtTokenUtils;
import project.shop.service.CartService;
import project.shop.service.ProductService;
import project.shop.service.UserService;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;
    private final ProductService productService;

    /**
     * [POST] /products/{productId}/cart?count=
     * 상품 장바구니에 담기
     */
    @PostMapping("/products/{productId}/cart")
    public void addCart(HttpServletRequest request,
                        @PathVariable Long productId, @RequestParam Integer count) {

        // 사용자 조회
        String email = jwtTokenUtils.getEmailFromHeader(request);
        User user = userService.findByEmail(email);

        // CartProduct 생성
        Product product = productService.findById(productId);
        CartProduct cartProduct = CartProduct.createCartProduct(product, product.getPrice(), count);

        // 장바구니에 CartProduct 담기
        Cart cart = user.getCart();
        cartService.addCartProduct(cart, cartProduct);
    }
}
