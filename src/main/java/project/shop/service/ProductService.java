package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.dto.product.CreateProductRequest;
import project.shop.dto.product.ReadProductResponse;
import project.shop.dto.product.UpdateProductRequest;
import project.shop.entity.product.Product;
import project.shop.entity.product.ProductCategory;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.ProductRepository;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ReadProductResponse save(CreateProductRequest dto) {

        Product product = Product.createProduct(dto.getName(), dto.getPrice(),
                dto.getStock(), ProductCategory.valueOf(dto.getProductCategory()));
        productRepository.save(product);

        return ReadProductResponse.createResponse(product);
    }

    public ReadProductResponse findById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
        return ReadProductResponse.createResponse(product);
    }

    public Page<ReadProductResponse> findAll(Pageable pageable) {

        Page<Product> page = productRepository.findAll(pageable);
        return page.map(product -> ReadProductResponse.createResponse(product));
    }

    @Transactional
    public ReadProductResponse update(Long productId, UpdateProductRequest dto) {

        // 조회
        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));

        // 수정
        product.updateProduct(dto.getName(), dto.getPrice(), dto.getStock());

        // 응답
        return ReadProductResponse.createResponse(product);
    }
}
