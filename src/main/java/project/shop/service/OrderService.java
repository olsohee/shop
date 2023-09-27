package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Order;
import project.shop.entity.OrderProduct;
import project.shop.entity.Product;
import project.shop.entity.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.OrderRepository;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Transactional
    public Long order(User user, Product product, Integer count) {

        // OrderProduct
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), count);

        // Order
        Order order = Order.createOrder(user, orderProduct);

        // Order 저장
        orderRepository.save(order);

        return order.getId();
    }

    public Order findById(Long orderId) {

        return orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));
    }
}
