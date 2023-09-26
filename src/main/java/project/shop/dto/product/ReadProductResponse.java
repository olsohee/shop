package project.shop.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import project.shop.entity.Product;

@Data
public class ReadProductResponse {

    private String name;
    private Integer price;

    public static ReadProductResponse createResponse(Product product) {

        ReadProductResponse response = new ReadProductResponse();
        response.name = product.getName();
        response.price = product.getPrice();
        return response;
    }
}
