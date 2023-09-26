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
    Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    Product product;
}
