package project.shop.dto.product;

import lombok.Data;
import project.shop.entity.product.Product;
import project.shop.entity.product.ProductCategory;

@Data
public class ReadProductResponse {

    private String name;
    private String productCategory;
    private Integer price;

    public static ReadProductResponse createResponse(Product product) {

        ReadProductResponse response = new ReadProductResponse();
        response.name = product.getName();
        response.productCategory = product.getProductCategory().getTitle();
        response.price = product.getPrice();
        return response;
    }
}
