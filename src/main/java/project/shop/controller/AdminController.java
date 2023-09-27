package project.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.shop.dto.product.CreateProductRequest;
import project.shop.dto.product.ReadProductResponse;
import project.shop.dto.product.UpdateProductRequest;
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

        // 저장 후 응답
        return productService.save(dto);
    }

    /**
     * [PUT] /admin/products/{productId}
     * 상품 수정
     */
    @PutMapping("/products/{productId}")
    public ReadProductResponse updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest dto) {

        // 수정
        return productService.update(productId, dto);
    }
}
