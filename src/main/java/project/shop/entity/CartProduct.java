package project.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CartProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    private Integer price;

    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

    // 생성 메서드
    public static CartProduct createCartProduct(Product product, Integer price, Integer count) {

        CartProduct cartProduct = new CartProduct();
        cartProduct.product = product;
        cartProduct.price = price;
        cartProduct.count = count;
        return cartProduct;
    }

    // 수정 메서드
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void increaseCount(Integer updateCount) {

        this.count += updateCount;
    }

    public void updateCount(Integer updateCount) {

        this.count = updateCount;
    }
}
