package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Product;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;
import project.shop.repository.ProductRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long save(Product product) {

        productRepository.save(product);
        return product.getId();
    }

    public Product findById(Long id) {

        return productRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_PRODUCT));
    }

    public Page<Product> findAll(Pageable pageable) {

         return productRepository.findAll(pageable);
    }

    @Transactional
    public void update(Long id, String name, Integer price, Integer stock) {

        // 조회
        Product product = findById(id);

        // 수정
        product.updateProduct(name, price, stock);
    }
}
