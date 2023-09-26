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

        cartProductRepository.save(cartProduct);
        cart.addCartProduct(cartProduct);
    }
}
