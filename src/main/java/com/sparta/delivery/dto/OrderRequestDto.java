package com.sparta.delivery.dto;

import com.sparta.delivery.model.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private Long restaurantId;
    private List<OrderDetail> food;
}
