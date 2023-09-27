package project.shop.entity.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.shop.entity.BaseEntity;
import project.shop.entity.cart.Cart;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;

    //== 연관관계 메서드 ==//
    public void addAddress(Address address) {

        this.addresses.add(address);
        address.setUser(this);
    }

    //== 생성 메서드 ==//
    public static User createUser(String username, String email,
                                  String password, String phoneNumber,
                                  Authority authority) {

        User user = new User();
        user.username = username;
        user.email = email;
        user.password = password;
        user.phoneNumber = phoneNumber;
        user.authority = authority;
        user.cart = Cart.createCart(); // 사용자 생성시 Cart도 함께 생성됨
        return user;
    }
}
