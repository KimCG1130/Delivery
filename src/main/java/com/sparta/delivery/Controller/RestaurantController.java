package com.sparta.delivery.Controller;

import com.sparta.delivery.dto.RestaurantDto;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Api(tags = {"Restaurant"})
@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;


    //@ApiOperation(value = "레스토랑 추가")
    @PostMapping("/restaurant/register")
    public Restaurant addRestaurant(
            @RequestBody RestaurantDto requestDto
    ) {
        return restaurantService.addRestaurant(requestDto);
    }

    //@ApiOperation(value = "모든 레스토랑 조회")
    @GetMapping("/restaurants")
    public List<Restaurant> findAllRestaurant() {

        return restaurantService.findAllRestaurant();
    }
}

