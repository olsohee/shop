package project.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.shop.dto.product.AdminProductResponse;
import project.shop.dto.product.ProductRequest;
import project.shop.dto.product.ProductResponse;
import project.shop.dto.product.UpdateProductRequest;
import project.shop.service.ProductService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final ProductService productService;

    /**
     * [POST] /admin/product
     * 상품 등록
     */
    @PostMapping("/product")
    public AdminProductResponse addProduct(@RequestPart ProductRequest dto,
                                           @RequestPart List<MultipartFile> files) throws IOException {

        // 저장 후 응답
        return productService.save(dto, files);
    }

    /**
     * [PUT] /admin/products/{productId}
     * 상품 수정
     */
    @PutMapping("/products/{productId}")
    public AdminProductResponse updateProduct(@PathVariable Long productId,
                                             @RequestPart UpdateProductRequest dto,
                                             @RequestPart List<MultipartFile> files) throws IOException {

        // 수정
        return productService.update(productId, dto, files);
    }
}
