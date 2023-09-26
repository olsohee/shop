package project.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
