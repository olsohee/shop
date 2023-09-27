package project.shop.dto.order;

import lombok.Data;
import project.shop.entity.order.Order;
import project.shop.entity.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
 * 주문 내역 목록 조회
 */
@Data
public class OrderListResponse {

   private List<EachOrderResponse> eachOrderResponses = new ArrayList<>();

   public static OrderListResponse createResponse(List<Order> orders) {

       OrderListResponse response = new OrderListResponse();
       for (Order order : orders) {
           response.eachOrderResponses.add(EachOrderResponse.createResponse(order));
       }
       return response;
   }


   /*
    * 개별 주문 내역 미리보기
    */
    @Data
    static class EachOrderResponse {

        private Long orderId;
        private Integer totalCount;
        private Integer totalPrice;
        private OrderStatus orderStatus;
        private LocalDateTime orderDate;
        private String productName; // 첫번째 주문 상품명

        public static EachOrderResponse createResponse(Order order) {

            EachOrderResponse response = new EachOrderResponse();
            response.orderId = order.getId();
            response.totalCount = order.getTotalCount();
            response.totalPrice = order.getTotalPrice();
            response.orderStatus = order.getOrderStatus();
            response.orderDate = order.getOrderDate();
            response.productName = order.getOrderProducts().get(0).getProduct().getName();
            return response;
        }
    }
}
