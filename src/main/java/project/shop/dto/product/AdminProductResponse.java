package project.shop.dto.product;

import lombok.Data;
import project.shop.entity.product.Product;
import project.shop.entity.product.ProductImage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class AdminProductResponse {

    private String name;
    private String productCategory;
    private Integer price;
    private Integer stock;
    private List<String> storeFilenames;

    public static AdminProductResponse createResponse(Product product) {

        AdminProductResponse response = new AdminProductResponse();
        response.name = product.getName();
        response.productCategory = product.getProductCategory().getTitle();
        response.price = product.getPrice();
        response.stock = product.getStock();
        response.storeFilenames = product.getProductImages().stream()
                                    .map(pi -> pi.getStoreFilename())
                                    .collect(Collectors.toList());
        return response;
    }
}