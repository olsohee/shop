package project.shop.dto.order;

import lombok.Data;

@Data
public class OrderRequest {

    private Long productId;
    private Integer count;
}
