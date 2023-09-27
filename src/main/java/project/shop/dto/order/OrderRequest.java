package project.shop.dto.order;

import lombok.Data;

import java.util.List;

/*
 * 주문 요청
 */
@Data
public class OrderRequest {

    private List<OrderProductRequest> orderProducts;

    @Data
    public static class OrderProductRequest {

        private Long productId;
        private Integer count;
    }
}
