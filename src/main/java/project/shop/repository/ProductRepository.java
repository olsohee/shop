package project.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.product.Product;
import project.shop.entity.product.ProductCategory;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByProductCategory(ProductCategory productCategory, Pageable pageable);
}
