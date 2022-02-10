package com.sparta.delivery.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long minOrderPrice;

    @Column(nullable = false)
    private Long deliveryFee;




//    public Restaurant(Long id, String name, int minOrderPrice, int deliveryFee) {
//        this.id = id;
//        this.name = name;
//        this.minOrderPrice = minOrderPrice;
//        this.deliveryFee = deliveryFee;
//    }
// 기존 복사 코드
    @Builder//생성자
    public Restaurant(String name, Long minOrderPrice, Long deliveryFee) {
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }


// 동석님코드
//    public Restaurant(RestaurantDto restaurantDto) {
//        this.name = restaurantDto.getName();
//        this.minOrderPrice = restaurantDto.getMinOrderPrice();
//        this.deliveryFee = restaurantDto.getDeliveryFee();
//    }

}
