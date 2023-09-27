package project.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

    // 생성 메서드
    public static OrderProduct createOrderProduct(Product product, Integer price, Integer count) {

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.price = price;
        orderProduct.count = count;
        orderProduct.product = product;
        return orderProduct;
    }

    // 수정 메서드
    public void setOrder(Order order) {
        this.order = order;
    }
}
