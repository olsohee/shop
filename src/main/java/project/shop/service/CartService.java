package project.shop.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.dto.cart.CartResponse;
import project.shop.entity.cart.Cart;
import project.shop.entity.cart.CartProduct;
import project.shop.entity.product.Product;
import project.shop.entity.user.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.jwt.JwtTokenUtils;
import project.shop.repository.ProductRepository;
import project.shop.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CartService {

    private final JwtTokenUtils jwtTokenUtils;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /*
     * 해당 상품이 장바구니에 이미 있는 상품인지, 아닌지를 판단하여 장바구니에 상품 추가
     */
    @Transactional
    public CartResponse addCartProduct(HttpServletRequest request, Long productId, Integer count) {

        // User
        User user = this.findUserFromRequest(request);

        // Cart
        Cart cart = user.getCart();

        // 해당 상품이 이미 장바구니에 있는 상품인지 확인
        CartProduct findCartProduct = findDuplicateCartProduct(cart, productId);

        if(findCartProduct != null) {
            // 이미 있는 상품이면 장바구니의 갯수만 수정
            this.updateCartProductCount(cart, findCartProduct, count);
        } else {
            // 새로운 상품이면 CartProduct 생성해서 Cart에 담아주기
            Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));
            CartProduct cartProduct = CartProduct.createCartProduct(product, product.getPrice(), count);
            this.addNewCartProduct(cart, cartProduct);
        }

        return CartResponse.createResponse(cart.getCartProducts(), cart);
    }

    /*
     * 사용자의 Cart 조회
     */
    public CartResponse findCart(HttpServletRequest request) {

        User user = this.findUserFromRequest(request);
        Cart cart = user.getCart();
        return CartResponse.createResponse(cart.getCartProducts(), cart);
    }

    /*
     * 장바구니에 담긴 상품 수량 증가
     */
    @Transactional
    public CartResponse increaseCartProductCount(HttpServletRequest request, Long productId) {

        // User 조회
        User user = this.findUserFromRequest(request);

        // Cart 조회
        Cart cart = user.getCart();

        CartProduct cartProduct = this.findByProductId(productId, cart.getCartProducts())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART_PRODUCT));

        // 현재 수량이 재고 수량이면 증가 불가
        if(cartProduct.getCount() == cartProduct.getProduct().getStock()) {
            throw new CustomException(ErrorCode.CANNOT_INCREASE_COUNT);
        }

        // 수량 증가
        cartProduct.increaseCount(1);
        cart.increaseTotalCount(1);
        cart.increaseTotalPrice(cartProduct.getPrice());

        return CartResponse.createResponse(cart.getCartProducts(), cart);
    }

    /*
     * 장바구니에 담긴 상품 수량 감소
     */
    @Transactional
    public CartResponse decreaseCartProductCount(HttpServletRequest request, Long productId) {

        // User 조회
        User user = this.findUserFromRequest(request);

        // Cart 조회
        Cart cart = user.getCart();

        CartProduct cartProduct = this.findByProductId(productId, cart.getCartProducts())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART_PRODUCT));

        // 현재 수량이 1개이면 감소 불가
        if(cartProduct.getCount() == 1) {
            throw new CustomException(ErrorCode.CANNOT_DECREASE_COUNT);
        }

        // 수량 감소
        cartProduct.decreaseCount(1);
        cart.decreaseTotalCount(1);
        cart.decreaseTotalPrice(cartProduct.getPrice());

        return CartResponse.createResponse(cart.getCartProducts(), cart);
    }


    // 장바구니에 이미 있는 상품인지 확인
    public CartProduct findDuplicateCartProduct(Cart cart, Long productId) {

        List<CartProduct> cartProducts = cart.getCartProducts();
        for(CartProduct cartProduct : cartProducts) {
            if(cartProduct.getProduct().getId().equals(productId)) {
                return cartProduct;
            }
        }
        return null;
    }

    //== private 메서드 ==//

    /*
     * request를 통해 사용자 조회
     */
    private User findUserFromRequest(HttpServletRequest request) {

        String email = jwtTokenUtils.getEmailFromHeader(request);
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }

    /*
     * 장바구니에 새로운 상품 추가
     */
    private void addNewCartProduct(Cart cart, CartProduct cartProduct) {

        // Cart에 CartProduct 담기
        cart.addCartProduct(cartProduct);

        // Cart의 totalPrice, totalCount 수정
        cart.increaseTotalCount(cartProduct.getCount());
        cart.increaseTotalPrice(cartProduct.getPrice() * cartProduct.getCount());
    }

    /*
     * 장바구니에 존재하는 상품 개수 변경
     */
    private void updateCartProductCount(Cart cart, CartProduct cartProduct, Integer updateCount) {

        Integer oldCount = cartProduct.getCount();

        if(oldCount < updateCount) {
            // 갯수 올리기
            int increaseCount = updateCount - oldCount;
            cartProduct.updateCount(updateCount);
            cart.increaseTotalCount(increaseCount);
            cart.increaseTotalPrice(increaseCount * cartProduct.getPrice());
        } else {
            // 갯수 줄이기
            int decreaseCount = oldCount - updateCount;
            cartProduct.updateCount(updateCount);
            cart.decreaseTotalCount(decreaseCount);
            cart.decreaseTotalPrice(decreaseCount * cartProduct.getPrice());
        }
    }

    /*
     * CartProduct 리스트에서 인자로 주어진 productId를 갖는 CartProduct 추출
     */
    private Optional<CartProduct> findByProductId(Long productId, List<CartProduct> cartProducts) {

        for(CartProduct cartProduct : cartProducts) {
            if(cartProduct.getProduct().getId().equals(productId)) {
                return Optional.of(cartProduct);
            }
        }
        return Optional.empty();
    }
}
