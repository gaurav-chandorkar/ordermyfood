package com.gaurav.restaurantsearchservice;

import com.gaurav.restaurantsearchservice.model.*;
import com.gaurav.restaurantsearchservice.repository.CuisineRepository;
import com.gaurav.restaurantsearchservice.repository.FoodItemRepository;
import com.gaurav.restaurantsearchservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class RestaurantSearchServiceApplication implements CommandLineRunner {

    @Autowired
    CuisineRepository cuisineRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    FoodItemRepository foodItemRepository;

    @Value("${user}")
    String  user;

    @Value("${password}")
    String password;

    @Value("${spring.datasource.username}")
    String dataSourceUserName;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantSearchServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(" print datasourceUserName: "+dataSourceUserName);
        System.out.println(" print user value: "+user);
        System.out.println(" print password value: "+password);

        Restaurant restaurant1 = new Restaurant("Udapi", "mumbai", 0.25, 100d);

        List<Cuisine> cuisineList = new ArrayList<>();

        cuisineList.add(Cuisine.builder().restaurant(restaurant1).name("Maharashtrian").build());
        cuisineList.add(Cuisine.builder().restaurant(restaurant1).name("Mughalai").build());
        cuisineList.add(Cuisine.builder().restaurant(restaurant1).name("Rajasthani").build());
        cuisineList.add(Cuisine.builder().restaurant(restaurant1).name("Gujarati").build());
        cuisineRepository.saveAll(cuisineList);

        List<FoodItem> foodItemList = new ArrayList<>();
        foodItemList.add(FoodItem.builder().foodName("Pav Bhaji").restaurant(restaurant1).cuisine(cuisineList
                .get(0)).price(100d).stock(50).build());
        foodItemList.add(FoodItem.builder().foodName("Chicken Mughalai").restaurant(restaurant1).cuisine(cuisineList
                .get(1)).price(400d).stock(50).build());
        foodItemRepository.saveAll(foodItemList);
        foodItemList.clear();

        restaurant1 = new Restaurant("Hotel sRk ", "gujarat", 0.4, 150d);
        restaurantRepository.save(restaurant1);
        foodItemList.add(FoodItem.builder().foodName("daal Khichdi").restaurant(restaurant1).cuisine(cuisineList
                .get(3)).price(150d).stock(50).build());
        foodItemList.add(FoodItem.builder().foodName("roti sabji").restaurant(restaurant1).cuisine(cuisineList
                .get(2)).price(100d).stock(50).build());
        foodItemRepository.saveAll(foodItemList);
        foodItemList.clear();

        restaurant1 = new Restaurant("Gujarat Snacks", "gujarat", 0.6, 100d);

        cuisineList.clear();

        cuisineList.add(Cuisine.builder().restaurant(restaurant1).name("Gujarati").build());
        cuisineList.add(Cuisine.builder().restaurant(restaurant1).name("South Indian").build());
        cuisineRepository.saveAll(cuisineList);

        foodItemList.add(FoodItem.builder().foodName("Dhokla").price(40d).restaurant(restaurant1).cuisine(cuisineList
                .get(0)).stock(50).build());
        foodItemList.add(FoodItem.builder().foodName("Masala Dosa").restaurant(restaurant1).cuisine(cuisineList
                .get(1)).price(70d).stock(50).build());
        foodItemList.add(FoodItem.builder().foodName("Idli").restaurant(restaurant1).cuisine(cuisineList
                .get(1)).price(50d).stock(50).build());
        foodItemRepository.saveAll(foodItemList);
        foodItemList.clear();

        restaurant1 = new Restaurant("gujarat Snacks", "Delhi", 3, 200d);
        restaurantRepository.save(restaurant1);

        restaurant1 = new Restaurant("Gujarat Snacks", "Bihar", 4, 200d);
        restaurantRepository.save(restaurant1);


    }

    public static OrderDetail getDemoOrderDetail(){


        BillPayment billPayment=BillPayment.builder().paymentMode("Upi")
                .date(new Date().getTime())
                .amountPaid(255d).build();

        OrderDetail orderDetail= OrderDetail.builder()
                .amountToBePaid(255)
                .billPayment(billPayment)
                .foodItems("10,22,12")
                .restId(11)
                .userId(2).build();
        return orderDetail;
    }
}
