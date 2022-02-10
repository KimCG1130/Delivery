package com.sparta.delivery.dto;

import com.sparta.delivery.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseDto {

    private String restaurantName;
    //private String foodName;
    //private Long foodQuantity;
    private List<FoodResponseDto> food;
    private Long price;
    private Long deliveryFee;
    private Long totalPrice;

    public OrderResponseDto(Order order, Long deliveryFee, List<FoodResponseDto> food) {
        this.restaurantName = order.getRestaurantName();
        this.food = food;
        this.deliveryFee = deliveryFee;
        this.totalPrice = order.getTotalPrice();
    }
}
