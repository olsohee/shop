package project.shop.entity.product;

import jakarta.persistence.*;
import lombok.*;
import project.shop.entity.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private ProductCategory productCategory;

    //== 생성 메서드 ==//
    public static Product createProduct(String name, Integer price, Integer stock, ProductCategory productCategory) {

        Product product = new Product();
        product.name = name;
        product.price = price;
        product.stock = stock;
        product.productCategory = productCategory;
        return product;
    }

    //== 비즈니스 메서드 ==//
    public void updateProduct(String name, Integer price, Integer stock) {

        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
