package project.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    private Integer totalCount;

    private Integer totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addOrderProduct(OrderProduct orderProduct) {

        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    // 생성 메서드
    public static Order createOrder(User user, List<OrderProduct> orderProducts) {

        Order order = new Order();
        order.orderStatus = OrderStatus.ORDER;
        order.orderDate = LocalDateTime.now();
        order.user = user;
        order.totalCount = 0;
        order.totalPrice = 0;
        for (OrderProduct orderProduct : orderProducts) {
            order.addOrderProduct(orderProduct);
            order.totalCount += orderProduct.getCount();
            order.totalPrice += orderProduct.getPrice() * orderProduct.getCount();
        }
        return order;
    }
}
