package project.shop.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.dto.order.OrderListResponse;
import project.shop.dto.order.OrderRequest;
import project.shop.dto.order.OrderResponse;
import project.shop.entity.order.Order;
import project.shop.entity.order.OrderProduct;
import project.shop.entity.product.Product;
import project.shop.entity.user.User;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.jwt.JwtTokenUtils;
import project.shop.repository.OrderRepository;
import project.shop.repository.ProductRepository;
import project.shop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {

    private final JwtTokenUtils jwtTokenUtils;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse order(HttpServletRequest request, OrderRequest dto) {

        // User
        User user = this.findUserFromRequest(request);

        //OrderProduct
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderRequest.OrderProductRequest orderProductDto : dto.getOrderProducts()) {
            Product product = productRepository.findById(orderProductDto.getProductId()).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
            OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), orderProductDto.getCount());
            orderProducts.add(orderProduct);
        }

        // Order
        Order order = Order.createOrder(user, orderProducts);

        // Order 저장
        orderRepository.save(order);

        // 응답
        return OrderResponse.createResponse(order);
    }

    public OrderListResponse findOrderList(HttpServletRequest request) {

        User user = this.findUserFromRequest(request);
        List<Order> orders = orderRepository.findByUser(user);
        return OrderListResponse.createResponse(orders);
    }

    public OrderResponse findOrder(HttpServletRequest request, Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));
        User user = this.findUserFromRequest(request);
        if(!order.getUser().equals(user)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        return OrderResponse.createResponse(order);
    }

    @Transactional
    public OrderResponse cancel(HttpServletRequest request, Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ORDER));
        User user = this.findUserFromRequest(request);
        if(!order.getUser().equals(user)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        // 주문 취소
        order.cancel();

        return OrderResponse.createResponse(order);
    }

    //== private 메서드 ==//

    /*
     * request를 통해 사용자 조회
     */
    private User findUserFromRequest(HttpServletRequest request) {

        String email = jwtTokenUtils.getEmailFromHeader(request);
        return userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
    }
}
