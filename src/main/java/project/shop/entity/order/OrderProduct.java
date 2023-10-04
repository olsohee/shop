package project.shop.entity.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.shop.entity.product.Product;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long id;

    private Integer price;

    private Integer count;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

    //== 생성 메서드 ==//
    public static OrderProduct createOrderProduct(Product product, Integer price, Integer count) {

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.price = price;
        orderProduct.count = count;
        orderProduct.product = product;

        product.reduceStock(count);
        return orderProduct;
    }

    //== 비즈니스 메서드 ==//
    public void setOrder(Order order) {

        this.order = order;
    }

    public void cancel() {

        this.getProduct().addStock(count);
    }
}
