package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Cart;
import project.shop.entity.CartProduct;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.CartProductRepository;
import project.shop.repository.CartRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    @Transactional
    public Long save(Cart cart) {

        cartRepository.save(cart);
        return cart.getId();
    }

    @Transactional
    public void addCartProduct(Cart cart, CartProduct cartProduct) {

        // Cart에 CartProduct 담기
        cartProductRepository.save(cartProduct);
        cart.addCartProduct(cartProduct);

        // Cart의 totalPrice, totalCount 수정
        cart.increaseTotalCount(cartProduct.getCount());
        cart.increaseTotalPrice(cartProduct.getPrice() * cartProduct.getCount());
    }

    // 이미 장바구니에 존재하는 상품의 갯수 수정
    @Transactional
    public void updateCartProductCount(Cart cart, CartProduct cartProduct, Integer updateCount) {

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

    // 장바구니의 CartProduct 수량 1개 늘리기
    @Transactional
    public void increaseCartProductCount(Cart cart, CartProduct cartProduct) {

        // 현재 수량이 재고 수량이면 증가 불가
        if(cartProduct.getCount() == cartProduct.getProduct().getStock()) {
            throw new CustomException(ErrorCode.CANNOT_INCREASE_COUNT);
        }

        cartProduct.increaseCount(1);
        cart.increaseTotalCount(1);
        cart.increaseTotalPrice(cartProduct.getPrice());
    }

    // 장바구니의 CartProduct 수량 1개 줄이기
    @Transactional
    public void decreaseCartProductCount(Cart cart, CartProduct cartProduct) {

        // 현재 수량이 1개이면 감소 불가
        if(cartProduct.getCount() == 1) {
            throw new CustomException(ErrorCode.CANNOT_DECREASE_COUNT);
        }

        cartProduct.decreaseCount(1);
        cart.decreaseTotalCount(1);
        cart.decreaseTotalPrice(cartProduct.getPrice());
    }
}
