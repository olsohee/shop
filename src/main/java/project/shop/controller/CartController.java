package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.shop.dto.cart.ReadCartResponse;
import project.shop.entity.Cart;
import project.shop.entity.CartProduct;
import project.shop.entity.Product;
import project.shop.entity.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.jwt.JwtTokenUtils;
import project.shop.service.CartService;
import project.shop.service.ProductService;
import project.shop.service.UserService;

import java.util.List;
import java.util.Optional;

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

    /**
     * [PUT] /cart/{productId}/increase
     * 상품 수량 증발
     */
    @PutMapping("/cart/{productId}/increase")
    public ReadCartResponse increaseCount(HttpServletRequest request,
                                          @PathVariable Long productId) {

        User user = findUserFromRequest(request);
        Cart cart = user.getCart();

        CartProduct cartProduct = findByProductId(productId, cart.getCartProducts())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART_PRODUCT));

        cartService.increaseCartProductCount(cart, cartProduct);
        return ReadCartResponse.createResponse(cart.getCartProducts(), cart);
    }

    /**
     * [PUT] /cart/{productId}/decrease
     * 상품 수량 감소
     */
    @PutMapping("/cart/{productId}/decrease")
    public ReadCartResponse decreaseCount(HttpServletRequest request,
                                          @PathVariable Long productId) {

        User user = findUserFromRequest(request);
        Cart cart = user.getCart();

        CartProduct cartProduct = findByProductId(productId, cart.getCartProducts())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART_PRODUCT));

        cartService.decreaseCartProductCount(cart, cartProduct);
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

    // CartProduct 리스트에서 인자로 주어진 productId를 갖는 CartProduct 추출
    public Optional<CartProduct> findByProductId(Long productId, List<CartProduct> cartProducts) {

        for(CartProduct cartProduct : cartProducts) {
            if(cartProduct.getProduct().getId().equals(productId)) {
                return Optional.of(cartProduct);
            }
        }
        return Optional.empty();
    }
}
