package com.sparta.delivery.service;
//- 주문 요청 시 배달 음식점 및 음식 정보 입력받음
//    1. 음식점 ID (restaurantId)
//    2. 음식 주문 정보 (foods)
//        1. 음식 ID (id)
//        2. 음식을 주문할 수량 (quantity)
//            1. 허용값: 1 ~ 100
//            2. 허용값이 아니면 에러 발생시킴
//
//- 주문 요청에 대한 응답으로 다음 정보를 포함시킴
//    1. 주문 음식점 이름 (restaurantName)
//    2. 주문 음식 정보 (foods)
//        - 주문 음식명 (name)
//        - 주문 수량 (quantity)
//        - 주문 음식의 가격 (price)
//            - 계산방법
//                - 주문 음식 1개의 가격 * 주문 수량
//    3. 배달비 (deliveryFee)
//        - 주문 음식점의 기본 배달비
//    4. 최종 결제 금액 (totalPrice)
//        - 계산방법
//            - 주문 음식 가격들의 총 합 + 배달비
//        - "주문 음식 가격들의 총 합" 이 주문 음식점의 "최소주문 가격" 을 넘지 않을 시 에러 발생시킴
//
//- 주문 조회
//    - 그동안 성공한 모든 주문 요청을 조회 가능

import com.sparta.delivery.dto.FoodResponseDto;
import com.sparta.delivery.dto.OrderRequestDto;
import com.sparta.delivery.dto.OrderResponseDto;
import com.sparta.delivery.model.Order;
import com.sparta.delivery.model.Food;
import com.sparta.delivery.model.OrderDetail;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.FoodRepository;
import com.sparta.delivery.repository.OrderDetailRepository;
import com.sparta.delivery.repository.OrderRepository;
import com.sparta.delivery.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderDetailRepository orderItemRepository;

    @Transactional
    public OrderResponseDto order(OrderRequestDto orderRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(orderRequestDto.getRestaurantId())
                .orElseThrow(
                        () -> new NullPointerException("해당 음식점이 없습니다.")
                );
        Long totalPrice = Long.valueOf(0);
        List<FoodResponseDto> foodResponseDtoList = new ArrayList<>();
        List<OrderDetail> orderDetail = orderRequestDto.getFood();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (OrderDetail tempOrderDetail : orderDetail) {

            Long quantity = tempOrderDetail.getQuantity();
            if (quantity < 1 || quantity > 100) {
                throw new IllegalArgumentException("음식 주문 수량은 1 ~ 100미만으로 입니다.");
            }

            Food food = foodRepository.findById(tempOrderDetail.getId())
                    .orElseThrow(() -> new NullPointerException("해당 음식이 없습니다."));

            OrderDetail orderItem = OrderDetail.builder()
                    .quantity(tempOrderDetail.getQuantity())
                    .name(food.getName())
                    .price(food.getPrice() * quantity)
                    .food(food)
                    .build();
            orderItemRepository.save(orderItem);
            FoodResponseDto foodResponseDto = new FoodResponseDto(orderItem);
            foodResponseDtoList.add(foodResponseDto);
            totalPrice += food.getPrice() * quantity;
            orderDetailList.add(orderItem);
        }

        if (totalPrice < restaurant.getMinOrderPrice()) {
            throw new IllegalArgumentException("음식점의 최소 주문 가격을 넘지 않았습니다.");
        }

        Long deliveryFee = restaurant.getDeliveryFee();
        totalPrice += deliveryFee;
        Order order = new Order(restaurant.getName(), totalPrice, orderDetailList);
        orderRepository.save(order);
        OrderResponseDto orderResponseDto = new OrderResponseDto(order, deliveryFee, foodResponseDtoList);
        return orderResponseDto;
    }

    @Transactional
    public List<OrderResponseDto> findAllOrder() {
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        List<Order> ordersList = orderRepository.findAll();

        for (Order order : ordersList) {
            Long deliveryFee = restaurantRepository.findByName(order.getRestaurantName()).getDeliveryFee();
            List<FoodResponseDto> foodResponseDtoList = new ArrayList<>();


            List<OrderDetail> orderDetailList  = orderItemRepository.findOrderDetailByOrder(order);
            for (OrderDetail orderItem : orderDetailList) {
                FoodResponseDto foodResponseDto = new FoodResponseDto(orderItem);
                foodResponseDtoList.add(foodResponseDto);
            }

            OrderResponseDto orderResponseDto = new OrderResponseDto(order, deliveryFee, foodResponseDtoList);
            orderResponseDtoList.add(orderResponseDto);
        }

        return orderResponseDtoList;
    }
}
