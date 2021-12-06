package com.gaurav.ordermanagementservice.service;

import com.gaurav.ordermanagementservice.model.OrderDetail;

public interface OrderService {

     void addOrder(OrderDetail orderDetail);
     void updateOrder(OrderDetail orderDetail);
    boolean cancelOrder(Long orderId);
    OrderDetail displayOrder(Long orderId, Long userId);
}
