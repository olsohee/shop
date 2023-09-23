package project.shop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class OrderProduct {

    @Id
    @GeneratedValue
    private Long orderProductId;
}
