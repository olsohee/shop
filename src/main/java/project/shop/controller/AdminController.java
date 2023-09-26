package project.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.product.CreateProductRequest;
import project.shop.dto.product.ReadProductResponse;
import project.shop.entity.Product;
import project.shop.service.ProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    /**
     * [POST] /admin/product
     * 상품 등록
     */
    @PostMapping("/product")
    public ReadProductResponse addProduct(@RequestBody CreateProductRequest dto) {

        // 저장
        Product product = Product.createProduct(dto.getName(), dto.getPrice(), dto.getStock());
        Long id = productService.save(product);

        // 조회
        Product savedProduct = productService.findById(id);
        return ReadProductResponse.createResponse(savedProduct);
    }
}
