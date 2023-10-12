package project.shop.entity.product;

import jakarta.persistence.*;
import lombok.*;
import project.shop.entity.BaseEntity;
import project.shop.exception.CustomException;
import project.shop.exception.ErrorCode;

import javax.transaction.xa.XAException;
import java.util.ArrayList;
import java.util.Iterator;
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

    private int salesCount;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
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
    public void updateProduct(String name, Integer price, Integer stock, ProductCategory productCategory) {

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.productCategory = productCategory;
    }

    public void updateProductImages(List<ProductImage> newProductImages) {

        // 기존 리스트 clear
        this.productImages.clear();

        // 기존 리스트에 새로운 ProductImage들을 추가
        for (ProductImage newProductImage : newProductImages) {
            productImages.add(newProductImage);
        }
    }

    public void reduceStock(int stock) {

        int restStock = this.stock - stock;
        if(restStock < 0) {
            throw new CustomException(ErrorCode.OUT_OF_STOCK);
        }

        this.stock = restStock;
    }

    public void addStock(int stock) {

        this.stock += stock;
    }

    public void addSalesCount(int salesCount) {

        this.salesCount += salesCount;
    }
}
