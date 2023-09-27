package project.shop.dto.order;

import lombok.Data;
import project.shop.entity.order.Order;
import project.shop.entity.order.OrderProduct;
import project.shop.entity.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * 주문 내역 단일 조회
 */
@Data
public class OrderResponse {

    private Long orderId;
    private Integer totalCount;
    private Integer totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
    private List<OrderProductResponse> orderProducts = new ArrayList<>();

    public static OrderResponse createResponse(Order order) {

        OrderResponse response = new OrderResponse();
        response.orderId = order.getId();
        response.totalCount = order.getTotalCount();
        response.totalPrice = order.getTotalPrice();
        response.orderStatus = order.getOrderStatus();
        response.orderDate = order.getOrderDate();

        for (OrderProduct orderProduct : order.getOrderProducts()) {
            response.orderProducts.add(OrderProductResponse.createResponse(orderProduct));
        }
        return response;
    }

    /*
     * 개별 주문 상품
     */
    @Data
    public static class OrderProductResponse {

        private String productName;
        private Integer price;
        private Integer count;

        static OrderProductResponse createResponse(OrderProduct orderProduct) {

            OrderProductResponse response = new OrderProductResponse();
            response.productName = orderProduct.getProduct().getName();
            response.price = orderProduct.getPrice();
            response.count = orderProduct.getCount();
            return response;
        }
    }
}
