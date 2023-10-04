package project.shop.dto.product;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private Integer price;
    private Integer stock;
    private String productCategory;
}
