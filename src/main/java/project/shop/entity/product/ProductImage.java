package project.shop.entity.product;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    private String uploadFileName;

    private String storeFileName;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    public static ProductImage createProductImage(String uploadFileName, String storeFileName) {

        ProductImage productImage = new ProductImage();
        productImage.uploadFileName = uploadFileName;
        productImage.storeFileName = storeFileName;
        return productImage;
    }
}
