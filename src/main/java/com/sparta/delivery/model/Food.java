package com.sparta.delivery.model;

import com.sparta.delivery.dto.FoodRequestDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor//파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor//모든 필드 값을 파라미터로 받는 생성자를 만듦

public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @ManyToOne
    @JoinColumn(name="restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Builder
    public Food(FoodRequestDto requestDto, Restaurant restaurant) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.restaurant = restaurant;
    }

}
