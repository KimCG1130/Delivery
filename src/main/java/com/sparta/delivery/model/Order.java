package com.sparta.delivery.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderDetail> food;


    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private Long totalPrice;


    @Builder
    public Order(String restaurantName, Long totalPrice, List<OrderDetail> orderDetail) {
        this.restaurantName = restaurantName;
        this.totalPrice = totalPrice;
        this.food = orderDetail;
    }
}
