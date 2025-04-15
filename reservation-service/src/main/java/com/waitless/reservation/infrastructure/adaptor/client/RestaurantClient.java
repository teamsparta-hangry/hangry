package com.waitless.reservation.infrastructure.adaptor.client;

import com.waitless.reservation.infrastructure.config.feign.RestaurantFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(
        name = "restaurant-service",
        url = "${gateway.url}",
        configuration = RestaurantFeignConfig.class
)
public interface RestaurantClient {

    @GetMapping("/restaurant-service/api/restaurants/{restaurantId}")
    void existRestaurant(@PathVariable UUID restaurantId);
}