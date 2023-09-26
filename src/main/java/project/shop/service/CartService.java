package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Cart;
import project.shop.entity.CartProduct;
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

    // 이미 장바구니에 존재하는 상품의 갯수 올리기
    @Transactional
    public void updateCartProductCount(Cart cart, CartProduct cartProduct, Integer updateCount) {

        // CartProduct의 count 수정
        cartProduct.increaseCount(updateCount);

        // Cart의 totalPrice, totalCount 수정
        cart.increaseTotalCount(updateCount);
        cart.increaseTotalPrice(updateCount * cartProduct.getPrice());
    }
}
