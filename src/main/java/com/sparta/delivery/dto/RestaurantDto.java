package com.sparta.delivery.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantDto {
    private String name;
    private Long minOrderPrice;
    private Long deliveryFee;
}
//희성님코드
//public class RestaurantDto {
//
//
//    private String name;
//    private int minOrderPrice;
//    private int deliveryFee;
//
//    public Restaurant toEntity() {
//        return Restaurant.builder()
//                .name(this.name)
//                .minOrderPrice(this.minOrderPrice)
//                .deliveryFee(this.deliveryFee)
//                .build();
//    }
//}
