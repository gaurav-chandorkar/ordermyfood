package com.gaurav.restaurantsearchservice.service.impl;

import com.gaurav.restaurantsearchservice.model.FoodItem;
import com.gaurav.restaurantsearchservice.model.Restaurant;
import com.gaurav.restaurantsearchservice.repository.FoodItemRepository;
import com.gaurav.restaurantsearchservice.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    FoodItemRepository foodItemRepository;

    @Autowired
    FoodItemServiceImpl(FoodItemRepository foodItemRepository){
        this.foodItemRepository=foodItemRepository;
    }

    @Override
    public List<FoodItem> findFoodItemsByRestaurantID(Restaurant restaurant) {

       return foodItemRepository.findFoodItemByRestaurant(restaurant);
    }

    @Override
    public FoodItem getFoodItemByID(long id){
       return foodItemRepository.findById(id).get();
    }

    @Override
    public FoodItem saveItem(FoodItem item) {
       return foodItemRepository.save(item);
    }
}
