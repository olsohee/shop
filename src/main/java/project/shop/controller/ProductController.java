package project.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import project.shop.dto.product.ReadProductResponse;
import project.shop.service.ProductService;


@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * [GET] /products
     * 상품 목록 조회
     */
    @GetMapping("/products")
    public Page<ReadProductResponse> findAll(@PageableDefault(size = 5, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        return productService.findAll(pageable);
    }

    /**
     * [GET] /products/{productId}
     * 상품 단일 조회
     */
    @GetMapping("/products/{productId}")
    public ReadProductResponse findOne(@PathVariable Long productId) {

        return productService.findById(productId);
    }
}
