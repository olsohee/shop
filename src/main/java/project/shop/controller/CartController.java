package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.shop.dto.cart.ReadCartResponse;
import project.shop.entity.Cart;
import project.shop.entity.CartProduct;
import project.shop.entity.Product;
import project.shop.entity.User;
import project.shop.jwt.JwtTokenUtils;
import project.shop.service.CartService;
import project.shop.service.ProductService;
import project.shop.service.UserService;

import java.util.List;

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
    public ReadCartResponse addCart(HttpServletRequest request,
                                    @PathVariable Long productId, @RequestParam Integer count) {

        // 사용자, 장바구니 조회
        User user = findUserFromRequest(request);
        Cart cart = user.getCart();

        // 해당 상품이 이미 장바구니에 있는 상품인지 확인
        CartProduct findCartProduct = findDuplicateCartProduct(productId, cart);
        if(findCartProduct != null) {
            // 이미 있는 상품이면 장바구니의 갯수만 수정
            cartService.updateCartProductCount(cart, findCartProduct, count);
        } else {
            // 새로운 상품이면 CartProduct 생성해서 Cart에 담아주기
            Product product = productService.findById(productId);
            CartProduct cartProduct = CartProduct.createCartProduct(product, product.getPrice(), count);
            cartService.addCartProduct(cart, cartProduct);
        }

        return ReadCartResponse.createResponse(cart.getCartProducts(), cart);
    }

    /**
     * [GET] /cart
     * 장바구니 조회
     */
    @GetMapping("/cart")
    public ReadCartResponse readCart(HttpServletRequest request) {

        User user = findUserFromRequest(request);
        Cart cart = user.getCart();
        return ReadCartResponse.createResponse(cart.getCartProducts(), cart);
    }

    // request를 통해 사용자 조회
    private User findUserFromRequest(HttpServletRequest request) {

        String email = jwtTokenUtils.getEmailFromHeader(request);
        return userService.findByEmail(email);
    }

    // 장바구니에 이미 있는 상품인지 확인
    private CartProduct findDuplicateCartProduct(Long id, Cart cart) {

        List<CartProduct> cartProducts = cart.getCartProducts();
        for(CartProduct cartProduct : cartProducts) {
            if(cartProduct.getProduct().getId().equals(id)) {
                return cartProduct;
            }
        }
        return null;
    }
}
