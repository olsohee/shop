package project.shop.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {

    private List<OrderProductRequest> orderProducts;

    @Data
    public static class OrderProductRequest {

        private Long productId;
        private Integer count;
    }
}
