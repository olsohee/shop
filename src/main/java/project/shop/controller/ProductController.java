package project.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.product.CreateProductRequest;
import project.shop.dto.product.CreateProductResponse;
import project.shop.entity.Product;
import project.shop.service.ProductService;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * [POST] /product
     * 상품 등록
     */
    @PostMapping("/product")
    public CreateProductResponse addProduct(@RequestBody CreateProductRequest dto) {

        // 저장
        Product product = Product.createProduct(dto.getName(), dto.getPrice(), dto.getStock());
        Long id = productService.save(product);

        // 조회
        Product savedProduct = productService.findById(id);
        return new CreateProductResponse(savedProduct.getName(), savedProduct.getPrice(), savedProduct.getStock());
    }

    /**
     * [GET] /products
     * 상품 전체 조회
     */

    /**
     * [GET] /products/{productId}
     * 상품 단일 조회
     */
}
