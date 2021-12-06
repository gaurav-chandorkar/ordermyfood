package com.gaurav.ordermanagementservice.repository;

import com.gaurav.ordermanagementservice.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetail, Long> {

    OrderDetail findByOrderIdAndUserId(Long orderId,Long userID);
}
