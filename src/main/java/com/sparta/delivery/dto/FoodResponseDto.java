package com.sparta.delivery.dto;

import com.sparta.delivery.model.OrderDetail;
import lombok.Getter;

@Getter
public class FoodResponseDto {
    private String name;
    private Long quantity;
    private Long price;

    public FoodResponseDto(OrderDetail orderDetail) {
        this.name = orderDetail.getName();
        this.quantity = orderDetail.getQuantity();
        this.price = orderDetail.getPrice();
    }
}
