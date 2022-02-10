package com.sparta.delivery.Controller;


import com.sparta.delivery.dto.OrderRequestDto;
import com.sparta.delivery.dto.OrderResponseDto;
import com.sparta.delivery.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문
    @PostMapping("/order/request")
    public OrderResponseDto orderFood(
            @RequestBody OrderRequestDto orderRequestDto
    ) {
        return orderService.order(orderRequestDto);
    }

    //성공 주문 조회
    @GetMapping("/order")
    public List<OrderResponseDto> findAllOrder() {
        return orderService.findAllOrder();
    }
}
