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

    private String uploadFilename;

    private String storeFilename;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    public static ProductImage createProductImage(String uploadFilename, String storeFilename) {

        ProductImage productImage = new ProductImage();
        productImage.uploadFilename = uploadFilename;
        productImage.storeFilename = storeFilename;
        return productImage;
    }
}
