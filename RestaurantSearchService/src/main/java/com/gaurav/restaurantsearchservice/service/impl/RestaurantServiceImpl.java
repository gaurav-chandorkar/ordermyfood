package com.gaurav.restaurantsearchservice.service.impl;

import com.gaurav.restaurantsearchservice.model.Restaurant;
import com.gaurav.restaurantsearchservice.repository.CuisineRepository;
import com.gaurav.restaurantsearchservice.repository.RestaurantRepository;
import com.gaurav.restaurantsearchservice.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private Logger logger = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final CuisineRepository cuisineRepository;

    @Autowired
    RestaurantServiceImpl(RestaurantRepository repository, CuisineRepository cuisineRepository) {
        this.restaurantRepository = repository;
        this.cuisineRepository = cuisineRepository;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {

        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant findRestaurantById(long id) {

        return restaurantRepository.findById(id).get();
    }

    @Override
    public List<Restaurant> getRestaurantByName(String name) {
        List<Restaurant> mList = restaurantRepository.findByNameContains(name);
        return mList;
    }

    @Override
    public List<Restaurant> searchRestaurant(String input) {


        List<Restaurant> mList = restaurantRepository.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(input, input);
        List<Restaurant> mRestaurantListByCuisineName = getRestaurantByCuisineName(input);
        return Stream.concat(mList.stream(), mRestaurantListByCuisineName.stream()).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> getRestaurantByCuisineName(String input) {
        List<Restaurant> restaurantList = new ArrayList<>();
        cuisineRepository.findByNameContainingIgnoreCase(input).forEach(cuisine -> restaurantList.add(cuisine.getRestaurant())
        );

        return restaurantList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> getRestaurantNearByUserLoc() {

        return restaurantRepository.findByDistanceKmLessThanEqual(0.4);
    }

   /* public List<Restaurant> searchRestaurant11(String input) {
        Restaurant restaurant = Restaurant.builder().name(input).location(input).build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                //.withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("location", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Restaurant> restaurantExample = Example.of(restaurant, matcher);

        List<Restaurant> mList = restaurantRepository.findAll(restaurantExample);
        //logger.info("searchRestaurant {}", mList);

        return mList;
    }*/
}
