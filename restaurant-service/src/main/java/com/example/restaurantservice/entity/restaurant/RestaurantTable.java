package com.example.restaurantservice.entity.restaurant;


import com.example.restaurantservice.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "restaurant_table")
public class RestaurantTable extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "table_id")
    private Long id;

    @Column(name = "table_name", length = 40, nullable = false)
    private String tableName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Builder
    public RestaurantTable(String tableName, Restaurant restaurant) {
        this.tableName = tableName;
        this.restaurant = restaurant;
    }
}

