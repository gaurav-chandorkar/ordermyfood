package com.gaurav.restaurantsearchservice.service;

import com.gaurav.restaurantsearchservice.model.Cuisine;
import com.gaurav.restaurantsearchservice.model.FoodItem;
import com.gaurav.restaurantsearchservice.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

//@SpringBootTest
class RestaurantServiceTest {

    Logger logger = LoggerFactory.getLogger(RestaurantServiceTest.class);

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    CuisineService cuisineService;

    @Autowired
    FoodItemService foodItemService;


   /* @Test
    public void getMenuByRestaurantId() {
        List<FoodItem> foodItemList =
                foodItemService.findFoodItemsByRestaurantID(Restaurant.builder().id(2L).build());
        logger.info("getMenuByRestaurantId {}", foodItemList);

    }

    @Test
    public void getRestaurantByName() {
        List<Restaurant> restaurants = restaurantService.getRestaurantByName("res");

        logger.info("getRestaurantByName {}", restaurants);
    }

    @Test
    public void searchRestaurant() {
        List<Restaurant> restaurants = restaurantService.searchRestaurant("guj");

        logger.info("getRestaurantByName {}", restaurants);
    }

    @Test
    public void searchCuisine() {
        List<Cuisine> cuisine = cuisineService.getCuisineByName("guj");

        logger.info("searchCuisine {}", cuisine);
    }

    @Test
    public void searchDistinctCuisine() {
        List<Cuisine> cuisine = cuisineService.getDistinctCuisineByName("Gujarati");

        logger.info("searchDistinctCuisine {}", cuisine);
    }

    @Test
    public void getNearByRestaurants() {
        List<Restaurant> restaurants = restaurantService.getRestaurantNearByUserLoc();
        logger.info(" getNearByRestaurants {}", restaurants);
    }*/
}