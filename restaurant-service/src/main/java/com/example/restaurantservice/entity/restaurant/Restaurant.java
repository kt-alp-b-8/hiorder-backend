package com.example.restaurantservice.entity.restaurant;

import com.example.restaurantservice.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "restaurant")
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "restaurant_name", length = 100, nullable = false)
    private String restaurantName;

    @Builder
    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
