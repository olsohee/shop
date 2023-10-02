package project.shop.entity.cart;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.shop.entity.product.Product;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    private Integer price;

    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Product product;

    //== 생성 메서드 ==//
    public static CartProduct createCartProduct(Product product, Integer price, Integer count) {

        CartProduct cartProduct = new CartProduct();
        cartProduct.product = product;
        cartProduct.price = price;
        cartProduct.count = count;
        return cartProduct;
    }

    //== 비즈니스 메서드 ==//
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void increaseCount(Integer updateCount) {

        this.count += updateCount;
    }

    public void decreaseCount(Integer updateCount) {

        this.count -= updateCount;
    }

    public void updateCount(Integer updateCount) {

        this.count = updateCount;
    }
}
