package project.shop.dto.product;

import lombok.Data;

@Data
public class UpdateProductRequest {

    private String name;
    private Integer price;
    private Integer stock;
}
