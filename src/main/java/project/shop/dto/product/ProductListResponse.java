package project.shop.dto.product;

import lombok.Data;
import project.shop.entity.product.Product;

@Data
public class ProductListResponse {

    private String name;
    private String productCategory;
    private Integer price;

    public static ProductListResponse createResponse(Product product) {

        ProductListResponse response = new ProductListResponse();
        response.name = product.getName();
        response.productCategory = product.getProductCategory().getTitle();
        response.price = product.getPrice();
        return response;
    }
}
