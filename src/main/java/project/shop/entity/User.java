package project.shop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "user")
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Cart cart;

    // 연관관계 편의 메서드
    public void addAddress(Address address) {

        this.addresses.add(address);
        address.setUser(this);
    }

    // 생성 메서드
    public static User createUser(String username, String email,
                                  String password, String phoneNumber,
                                  Authority authority, Cart cart) {

        User user = new User();
        user.username = username;
        user.email = email;
        user.password = password;
        user.phoneNumber = phoneNumber;
        user.authority = authority;
        user.cart = cart; // 사용자 생성시 Cart도 함께 생성됨
        return user;
    }
}
