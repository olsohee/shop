package project.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
public class Product extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    // 생성 메서드
    public static Product createProduct(String name, Integer price, Integer stock) {

        Product product = new Product();
        product.name = name;
        product.price = price;
        product.stock = stock;
        return product;
    }
}
