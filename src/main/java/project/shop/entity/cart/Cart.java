package project.shop.entity.cart;

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

    //== 연관관계 메서드 ==//
    public void addCartProduct(CartProduct cartProduct) {

        this.cartProducts.add(cartProduct);
        cartProduct.setCart(this);
    }

    //== 생성 메서드 ==//
    public static Cart createCart() {

        Cart cart = new Cart();
        cart.totalCount = 0;
        cart.totalPrice = 0;
        return cart;
    }


    //== 비즈니스 메서드 ==//
    public void increaseTotalCount(Integer increaseCount) {

        this.totalCount += increaseCount;
    }

    public void increaseTotalPrice(Integer increasePrice) {

        this.totalPrice += increasePrice;
    }

    public void decreaseTotalCount(Integer decreaseCount) {

        this.totalCount -= decreaseCount;
    }

    public void decreaseTotalPrice(Integer decreasePrice) {

        this.totalPrice -= decreasePrice;
    }
}
