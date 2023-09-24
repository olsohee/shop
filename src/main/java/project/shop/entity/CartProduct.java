package project.shop.entity;

import jakarta.persistence.*;

@Entity
public class CartProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    private Integer price;

    private Integer count;

    @ManyToOne
    Cart cart;

    @ManyToOne
    Product product;
}
