package project.shop.entity.user;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    private String name;

    private String city;

    private String street;

    private String zipcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    //== 생성 메서드 ==//
    public static Address createAddress(String name, String city, String street, String zipcode) {

        Address address = new Address();
        address.name = name;
        address.city = city;
        address.street = street;
        address.zipcode = zipcode;
        return address;
    }

    //== 비즈니스 메서드 ==//
    public void setUser(User user) {
        this.user = user;
    }
}
