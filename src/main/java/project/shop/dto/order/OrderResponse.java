package project.shop.dto.order;

import lombok.Data;
import project.shop.entity.OrderStatus;
import project.shop.repository.OrderRepository;

@Data
public class OrderResponse {

    private Long orderId;
    private String productName;
    private Integer price;
    private Integer count;
    private Integer totalPrice;
    private OrderStatus orderStatus;

    public static OrderResponse createResponse(Long orderId, String productName, Integer price, Integer count, OrderStatus orderStatus) {

        OrderResponse response = new OrderResponse();
        response.orderId = orderId;
        response.productName = productName;
        response.price = price;
        response.count = count;
        response.totalPrice = price * count;
        response.orderStatus = orderStatus;
        return response;
    }
}
