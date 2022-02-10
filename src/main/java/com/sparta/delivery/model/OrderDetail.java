package com.sparta.delivery.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @ManyToOne(cascade = CascadeType.ALL)//영속성 전이
    private Food food;

    @ManyToOne
    private Order order;

    @Builder
    public OrderDetail(Long quantity, String name, Long price, Food food) {
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.food = food;
    }

}