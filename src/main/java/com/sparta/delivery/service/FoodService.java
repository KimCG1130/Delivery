package com.sparta.delivery.service;

import com.sparta.delivery.dto.FoodRequestDto;
import com.sparta.delivery.model.Food;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.FoodRepository;
import com.sparta.delivery.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//2. 음식 등록 및 메뉴판 조회
//    - 음식점 ID 및 음식 정보 입력받아 등록
//        1. 음식점 ID (restaurantId)
//            1. 음식점 DB 테이블 ID
//        2. 음식명 (name)
//            1. 같은 음식점 내에서는 음식 이름이 중복될 수 없음 (예. '자담치킨 강남점'에 '후라이드치킨' 이 이미 등록되어 있다면 중복하여 등록할 수 없지만, 다른 음식점인 '맘스터치 강남점'에는 '후라이드치킨' 을 등록 가능)
//        3. 가격 (price)
//            1. 허용값: 100원 ~ 1,000,000원
//            2. 100 원 단위로만 입력 가능 (예. 2,220원 입력 시 에러발생. 2,300원 입력 가능)
//            3. 허용값이 아니거나 100원 단위 입력이 아닌 경우 에러 발생시킴
//
//    - 메뉴판 조회
//        - 하나의 음식점에 등록된 모든 음식 정보 조회
//            1. 등록 시 입력한 음식 정보 (name, price)
//            2. DB 테이블 ID (id)
//
//
//    [API 명세서](https://www.notion.so/065b1715462a4b259ecfc993da90f7c7)

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void addFood(
            Long restaurantId,
            List<FoodRequestDto> requestDtoList
    ) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElse(null);

        if (restaurant == null) {
            throw new IllegalArgumentException("해당 음식점이 없습니다.");
        }

        for (FoodRequestDto requestDto : requestDtoList) {//향상된 for문,https://java119.tistory.com/107  공부하기

            Long price = requestDto.getPrice();
            if (price < 100) {
                throw new IllegalArgumentException("음식 가격이 100원 미만입니다.");
            }

            if (price > 1000000) {
                throw new IllegalArgumentException("음식 가격이 1,000,000원을 초과했습니다.");
            }

            if (price % 100 > 0) {
                throw new IllegalArgumentException("음식 가격이 100원 단위로 입력되었습니다.");
            }

            Optional<Food> found = foodRepository.findFoodByRestaurantAndName(restaurant, requestDto.getName());
            if(found.isPresent()){
                throw new IllegalArgumentException("중복된 이름의 음식이 존재합니다.");
            }



            Food food = new Food(requestDto, restaurant);
            foodRepository.save(food);
        }
    }

    @Transactional
    public List<Food> findAllFoods(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(
                        () -> new NullPointerException("해당 음식점이 없습니다."));

        return foodRepository.findFoodsByRestaurant(restaurant);
    }
}

