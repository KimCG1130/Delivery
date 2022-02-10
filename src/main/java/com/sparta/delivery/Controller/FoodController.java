package com.sparta.delivery.Controller;

import com.sparta.delivery.dto.FoodRequestDto;
import com.sparta.delivery.model.Food;
import com.sparta.delivery.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;


    //음식등록
    @PostMapping("/restaurant/{restaurantId}/food/register")//Pathvariable을 통해 레스토랑 id 가져오기
    public void addFood(@PathVariable Long restaurantId, @RequestBody List<FoodRequestDto> requestDtoList
    ) {
        foodService.addFood(restaurantId, requestDtoList);
    }

    //음식리스트 조회
   @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> findAllFoods(@PathVariable Long restaurantId) {
        return foodService.findAllFoods(restaurantId);
    }
}
