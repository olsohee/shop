package project.shop.entity;

import jakarta.persistence.*;

@Entity
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
}
