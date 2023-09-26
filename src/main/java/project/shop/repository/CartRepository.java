package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
