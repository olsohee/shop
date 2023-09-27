package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.cart.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
}
