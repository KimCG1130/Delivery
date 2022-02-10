package com.sparta.delivery.repository;


import com.sparta.delivery.model.Food;
import com.sparta.delivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findFoodsByRestaurant(Restaurant restaurant);//레스토랑에 있는 음식리스트 찾기
    Optional<Food> findFoodByRestaurantAndName(Restaurant restaurant, String foodName);//레스토랑에 있는 음식 하나 찾기

}
