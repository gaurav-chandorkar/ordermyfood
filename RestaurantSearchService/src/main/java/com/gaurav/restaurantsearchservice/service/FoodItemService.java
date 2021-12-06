package com.gaurav.restaurantsearchservice.service;

import com.gaurav.restaurantsearchservice.model.FoodItem;
import com.gaurav.restaurantsearchservice.model.Restaurant;

import java.util.List;

public interface FoodItemService {

    List<FoodItem> findFoodItemsByRestaurantID(Restaurant restaurant);

    FoodItem getFoodItemByID(long id);

    FoodItem saveItem(FoodItem item);
}
