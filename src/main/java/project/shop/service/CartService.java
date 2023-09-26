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
        cart.addTotalCount(cartProduct.getCount());
        cart.addTotalPrice(cartProduct.getPrice() * cartProduct.getCount());
    }

    @Transactional
    public void updateCartProductCount(Cart cart, CartProduct cartProduct, Integer updateCount) {

        // CartProduct의 가격 수정
        cartProduct.updateCount(updateCount);

        // Cart의 totalPrice, totalCount 수정
        cart.addTotalCount(updateCount);
        cart.addTotalPrice(updateCount * cartProduct.getPrice());
    }
}
