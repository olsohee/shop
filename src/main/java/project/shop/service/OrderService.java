package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.dto.order.OrderRequest;
import project.shop.entity.Order;
import project.shop.entity.OrderProduct;
import project.shop.entity.Product;
import project.shop.entity.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public Order findById(Long orderId) {

        return orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));
    }

    @Transactional
    public Long order(User user, List<OrderRequest.OrderProductRequest> orderProductDtos) {

        //OrderProduct
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderRequest.OrderProductRequest dto : orderProductDtos) {
            Product product = productService.findById(dto.getProductId());
            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), dto.getCount());
            orderProducts.add(orderProduct);
        }

        // Order
        Order order = Order.createOrder(user, orderProducts);

        // Order 저장
        orderRepository.save(order);

        return order.getId();
    }
}
