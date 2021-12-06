package com.gaurav.ordermanagementservice.service;

import com.gaurav.ordermanagementservice.model.OrderDetail;
import com.gaurav.ordermanagementservice.repository.OrderRepository;
import com.gaurav.ordermanagementservice.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    private RestaurantRepository restaurantRepository;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderServiceImpl(OrderRepository orderRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository=restaurantRepository;
    }

    @Override
    public void addOrder(OrderDetail orderDetail) {
       // restaurantRepository.save(orderDetail.getRestaurant());
        orderRepository.save(orderDetail);
    }

    @Override
    public void updateOrder(OrderDetail orderDetail) {
        orderRepository.save(orderDetail);
    }

    @Override
    public boolean cancelOrder(Long orderId) {
        try {
            orderRepository.deleteById(orderId);
            return true;
        } catch (Exception e) {
            logger.info("error occured while deleting order {}", e.getMessage());
        }
        return false;
    }

    @Override
    public OrderDetail displayOrder(Long orderId, Long userId) {
        try {

            return orderRepository.findByOrderIdAndUserId(orderId, userId);
        } catch (Exception e) {
            logger.info("error occurred while fetching order {}", e.getMessage());
        }
        return null;

    }
}
