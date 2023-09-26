package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Product;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.ProductRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Long save(Product product) {

        productRepository.save(product);
        return product.getId();
    }

    public Product findById(Long id) {

        return productRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
    }
}
