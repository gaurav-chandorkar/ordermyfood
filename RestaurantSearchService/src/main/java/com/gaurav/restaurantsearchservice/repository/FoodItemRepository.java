package com.gaurav.restaurantsearchservice.repository;

import com.gaurav.restaurantsearchservice.model.FoodItem;
import com.gaurav.restaurantsearchservice.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem,Long> {

    List<FoodItem> findFoodItemByRestaurant(Restaurant restaurant);
}
