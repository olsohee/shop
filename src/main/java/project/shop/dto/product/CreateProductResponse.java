package project.shop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateProductResponse {

    private String name;
    private Integer price;
    private Integer stock;
}
