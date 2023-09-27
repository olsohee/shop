package project.shop.dto.order;

import lombok.Data;
import project.shop.entity.Order;
import project.shop.entity.OrderProduct;
import project.shop.entity.OrderStatus;

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

    public static OrderResponse createResponse(Order order, List<OrderProduct> orderProducts) {

        OrderResponse response = new OrderResponse();
        response.orderId = order.getId();
        response.totalCount = order.getTotalCount();
        response.totalPrice = order.getTotalPrice();
        response.orderStatus = order.getOrderStatus();
        response.orderDate = order.getOrderDate();
        for (OrderProduct orderProduct : orderProducts) {
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
