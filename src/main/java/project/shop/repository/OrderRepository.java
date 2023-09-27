package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.order.Order;
import project.shop.entity.user.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
