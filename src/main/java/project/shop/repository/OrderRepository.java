package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
