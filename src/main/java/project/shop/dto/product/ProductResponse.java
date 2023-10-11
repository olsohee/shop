package project.shop.dto.product;

import lombok.Data;
import project.shop.entity.product.Product;
import project.shop.entity.product.ProductImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductResponse {

    private String name;
    private String productCategory;
    private Integer price;
    private List<String> storeFilenames;

    public static ProductResponse createResponse(Product product) {

        ProductResponse response = new ProductResponse();
        response.name = product.getName();
        response.productCategory = product.getProductCategory().getTitle();
        response.price = product.getPrice();
        response.storeFilenames = product.getProductImages().stream()
                                    .map(pi -> pi.getStoreFilename())
                                    .collect(Collectors.toList());
        return response;
    }
}
