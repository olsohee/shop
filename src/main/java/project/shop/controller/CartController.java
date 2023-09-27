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

        return cartService.addCartProduct(request, productId, count);
    }

    /**
     * [GET] /cart
     * 장바구니 조회
     */
    @GetMapping("/cart")
    public ReadCartResponse readCart(HttpServletRequest request) {

        return cartService.findCart(request);
    }

    /**
     * [PUT] /cart/{productId}/increase
     * 상품 수량 증가
     */
    @PutMapping("/cart/{productId}/increase")
    public ReadCartResponse increaseCount(HttpServletRequest request,
                                          @PathVariable Long productId) {

        return cartService.increaseCartProductCount(request, productId);
    }

    /**
     * [PUT] /cart/{productId}/decrease
     * 상품 수량 감소
     */
    @PutMapping("/cart/{productId}/decrease")
    public ReadCartResponse decreaseCount(HttpServletRequest request,
                                          @PathVariable Long productId) {

        return cartService.decreaseCartProductCount(request, productId);
    }





}
