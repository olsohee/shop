package project.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.shop.dto.cart.CartResponse;
import project.shop.service.CartService;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * [POST] /products/{productId}/cart?count=
     * 상품 장바구니에 담기
     */
    @PostMapping("/products/{productId}/cart")
    public CartResponse addCart(HttpServletRequest request,
                                @PathVariable Long productId, @RequestParam Integer count) {

        return cartService.addCartProduct(request, productId, count);
    }

    /**
     * [GET] /cart
     * 장바구니 조회
     */
    @GetMapping("/cart")
    public CartResponse readCart(HttpServletRequest request) {

        return cartService.findCart(request);
    }

    /**
     * [PUT] /cart/{productId}/increase
     * 상품 수량 증가
     */
    @PutMapping("/cart/{productId}/increase")
    public CartResponse increaseCount(HttpServletRequest request,
                                      @PathVariable Long productId) {

        return cartService.increaseCartProductCount(request, productId);
    }

    /**
     * [PUT] /cart/{productId}/decrease
     * 상품 수량 감소
     */
    @PutMapping("/cart/{productId}/decrease")
    public CartResponse decreaseCount(HttpServletRequest request,
                                      @PathVariable Long productId) {

        return cartService.decreaseCartProductCount(request, productId);
    }
}
