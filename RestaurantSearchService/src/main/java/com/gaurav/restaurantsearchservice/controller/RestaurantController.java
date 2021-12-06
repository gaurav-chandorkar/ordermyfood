package com.gaurav.restaurantsearchservice.controller;

import com.gaurav.restaurantsearchservice.model.FoodItem;
import com.gaurav.restaurantsearchservice.model.OrderDetail;
import com.gaurav.restaurantsearchservice.model.Restaurant;
import com.gaurav.restaurantsearchservice.model.Test;
import com.gaurav.restaurantsearchservice.service.FoodItemService;
import com.gaurav.restaurantsearchservice.service.RestaurantService;
import com.gaurav.restaurantsearchservice.service.impl.FoodItemServiceImpl;
import com.gaurav.restaurantsearchservice.service.impl.RestaurantServiceImpl;
import com.gaurav.restaurantsearchservice.util.PrintOrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/ordermyfood/")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    FoodItemService foodItemService;
    @Autowired
    RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(RestaurantController.class);


    @RequestMapping("restaurant-nearby-user")
    public List<Restaurant> getRestaurantNearByUserLoc() {
        List<Restaurant> restaurants = restaurantService.getRestaurantNearByUserLoc();
        if (restaurants != null) {
            return restaurants;
        } else {
            logger.info("No restaurant found for the given location");
            return null;
        }

    }

    @RequestMapping("search-restaurant/{input}")
    public List<Restaurant> searchRestaurant(@PathVariable String input) {
        List<Restaurant> restaurants = restaurantService.searchRestaurant(input);
        if (restaurants != null) {
            return restaurants;
        } else {
            logger.info("No restaurant found for the given location");
            return null;
        }

    }

    @RequestMapping("restaurant-menu/{restId}")
    public List<FoodItem> getFoodMenuForRestaurant(@PathVariable long restId) {
        try {
            List<FoodItem> foodItemList =
                    foodItemService.findFoodItemsByRestaurantID(Restaurant.builder().id(restId).build());
            return foodItemList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("No Menu found for restaurant ");
            return null;
        }
    }

    @RequestMapping(value = "place-order/{paymentMode}", method = RequestMethod.POST)
    public PrintOrderDetail processOrder(@RequestBody OrderDetail orderDetail, @PathVariable String paymentMode) {

        try {
            logger.info("processOrder  called ");
            updateAmountToBePaid(orderDetail);
            Boolean placedOrder = restTemplate.postForObject("http://order-management-service/place-order/" + paymentMode, orderDetail, Boolean.class);
            logger.info("processOrder  status {} ", placedOrder);
            return Boolean.TRUE.equals(placedOrder) ? displayOrder(orderDetail) : printErrorMessage("Order could not be placed..");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Order could not be placed");
            return printErrorMessage("Order could not be placed..");
        }
    }


    @RequestMapping(value = "update-order/{paymentMode}", method = RequestMethod.POST)
    public PrintOrderDetail updateOrder(@RequestBody OrderDetail orderDetail, @PathVariable String paymentMode) {

        try {
            updateAmountToBePaid(orderDetail);
            rollBackStockForOriginalOrder(orderDetail.getOrderId());
            logger.info("updateOrder  called ");
            Boolean placedOrder = restTemplate.postForObject("http://order-management-service/place-order/" + paymentMode, orderDetail, Boolean.class);
            logger.info("updateOrder  status {} ", placedOrder);
            return Boolean.TRUE.equals(placedOrder) ? displayOrder(orderDetail) : printErrorMessage("Order could not be updated..");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Order could not be updated");
            return printErrorMessage("Order could not be updated..");
        }
    }

    @RequestMapping(value = "cancel-order/{orderId}", method = RequestMethod.PUT)
    public PrintOrderDetail cancelOrder(@PathVariable long orderId) {

        try {
            rollBackStockForOriginalOrder(orderId);
            Boolean orderCanceled =
                    restTemplate.postForObject("http://order-management-service/cancel-order/" + orderId, null, Boolean.class);
            if (orderCanceled != null && orderCanceled) {
                logger.info("send notification to Delivery team about cancelling order");
                return printErrorMessage("Order canceled successfully!");
            } else {
                return printErrorMessage("Order could not be canceled..");

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Order could not be canceled");
            return printErrorMessage("Order could not be canceled..");
        }
    }

    private void rollBackStockForOriginalOrder(long orderId) {
        try {
            OrderDetail orderDetail =
                    restTemplate.getForObject("http://order-management-service/get-order-detail/" + orderId, OrderDetail.class);
            if (orderDetail != null && orderDetail.getFoodItems() != null
                    && orderDetail.getFoodItems().trim().length() > 1) {
                String[] foodItems = orderDetail.getFoodItems().trim().split(",");
                Arrays.stream(foodItems).forEach(foodItem -> {
                    FoodItem item = foodItemService.getFoodItemByID(Long.parseLong(foodItem));
                    logger.info(" rollBackStockForOriginalOrder food item name:{} price {}  ", item.getFoodName(), item.getPrice());
                    item.setStock(item.getStock() + 1);
                    foodItemService.saveItem(item);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("roll back could not be dome");
        }
    }

    private void updateAmountToBePaid(OrderDetail orderDetail) {
        double amount = 0;
        if (orderDetail.getFoodItems().trim().length() > 1) {
            amount = Arrays.stream(orderDetail.getFoodItems().trim().split(",")).map(item ->
                    foodItemService.getFoodItemByID(Long.parseLong(item))).mapToDouble(foodItem -> foodItem.getPrice()).sum();
        }
        logger.info("updateAmountToBePaid amount {}", amount);
        orderDetail.setAmountToBePaid(amount);
    }

    private PrintOrderDetail displayOrder(OrderDetail orderDetail) {
        PrintOrderDetail printOrderDetail;
        if (orderDetail.getFoodItems().trim().length() < 1) {
            printOrderDetail = PrintOrderDetail.builder().message("Please add food from menu to place order").build();
            return printOrderDetail;
        }
        String[] foodItems = orderDetail.getFoodItems().trim().split(",");
        printOrderDetail = PrintOrderDetail.builder().build();
        printOrderDetail.setFoodMap(new HashMap<String, Double>());
        Arrays.stream(foodItems).forEach(foodItem -> {
                    logger.info(" food item id:{}", foodItem);
                    FoodItem item = foodItemService.getFoodItemByID(Long.parseLong(foodItem));
                    logger.info(" food item name:{} price {}  ", item.getFoodName(), item.getPrice());
                    item.setStock(item.getStock() - 1);
                    foodItemService.saveItem(item);
                    printOrderDetail.getFoodMap().put(item.getFoodName(), item.getPrice());
                }
        );
        logger.info("food map: {}", printOrderDetail.getFoodMap());
        printOrderDetail.setMessage("Your total bill Amount is: " + orderDetail.getAmountToBePaid());
        logger.info("Sending notification to the Delivery Team");
        return printOrderDetail;
    }

    private PrintOrderDetail printErrorMessage(String msg) {
        return PrintOrderDetail.builder().message(msg).foodMap(null).build();
    }

    @RequestMapping("test")
    public void test() {
        logger.info("1 test ");

        Test test = restTemplate.postForObject("http://order-management-service/order", "Take Hello", Test.class);
        assert test != null;
        logger.info("test {}", test.str);
    }

    @RequestMapping("test1")
    public String test1() {
        logger.info("1 test1 ");

        String test = restTemplate.getForObject("http://order-management-service/test", String.class);
        assert test != null;
        logger.info("test {}", test);
        return test;
    }

}
