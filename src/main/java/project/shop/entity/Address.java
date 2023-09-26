package project.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    private String city;

    private String street;

    private String zipcode;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    public void setUser(User user) {
        this.user = user;
    }
}
