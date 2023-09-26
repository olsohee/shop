package project.shop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    private Integer totalCount;

    private Integer totalPrice;

    @OneToMany(mappedBy = "cart")
    List<CartProduct> cartProducts = new ArrayList<>();

    // 생성 메서드
    public static Cart createCart() {

        Cart cart = new Cart();
        cart.totalCount = 0;
        cart.totalPrice = 0;
        return cart;
    }

    // 연관관계 편의 메서드
    public void addCartProduct(CartProduct cartProduct) {

        this.cartProducts.add(cartProduct);
        cartProduct.setCart(this);
    }

    // 수정 메서드
    public void addTotalCount(Integer updateCount) {

        this.totalCount += updateCount;
    }

    public void addTotalPrice(Integer updatePrice) {

        this.totalPrice += updatePrice;
    }
}
