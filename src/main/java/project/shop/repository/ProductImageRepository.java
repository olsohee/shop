package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.product.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}
