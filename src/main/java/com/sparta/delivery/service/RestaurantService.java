package com.sparta.delivery.service;

import com.sparta.delivery.dto.RestaurantDto;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {


    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Restaurant addRestaurant(RestaurantDto requestDto) {
        //Restaurant restaurant = requestDto.toEntity();

        Long minOrderPrice = requestDto.getMinOrderPrice();
        Long deliveryFee = requestDto.getDeliveryFee();
        if(!(1000 <= minOrderPrice && minOrderPrice <= 100000)) {
            throw new IllegalArgumentException("최소주문 가격 허용값을 벗어났습니다.");
        }

        if(minOrderPrice % 100 > 0) {
            throw new IllegalArgumentException("100원 단위로 입력하지 않았습니다.");
        }

        if(0 > deliveryFee || deliveryFee > 10_000) {
            throw new IllegalArgumentException("기본 배달비 허용값을 벗어났습니다.");
        }

        if(deliveryFee % 500 > 0) {
            throw new IllegalArgumentException("기본 배달비 500원 단위로 입력하지 않았습니다.");
        }

        //Restaurant restaurant = new Restaurant(requestDto);





        Restaurant restaurant = Restaurant
                .builder()
                .name(requestDto.getName())
                .minOrderPrice(minOrderPrice)
                .deliveryFee(requestDto.getDeliveryFee())
                .build();

        restaurantRepository.save(restaurant);

        return restaurant;


        //return restaurantRepository.save(restaurant);
    }

    @Transactional
    public List<Restaurant> findAllRestaurant() {
        return restaurantRepository.findAll();
    }
}

