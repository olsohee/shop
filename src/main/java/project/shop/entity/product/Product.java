package project.shop.entity.product;

import jakarta.persistence.*;
import lombok.*;
import project.shop.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "product")
    List<ProductImage> productImages = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void addProductImage(ProductImage productImage) {

        productImages.add(productImage);
        productImage.product = this;
    }

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

    public void updateProductImages(List<ProductImage> productImages) {

        // 기존 List<ProductImage> 와의 관계가 끊기고 새로운 List<ProductImage>가 연결됨
        this.productImages = productImages;
    }
}
