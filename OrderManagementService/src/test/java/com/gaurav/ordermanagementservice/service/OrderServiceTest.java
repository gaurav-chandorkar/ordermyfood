package com.gaurav.ordermanagementservice.service;

import com.gaurav.ordermanagementservice.model.BillPayment;
import com.gaurav.ordermanagementservice.model.OrderDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderServiceImpl orderService;

    @Test
    public void placeOder(){
        BillPayment billPayment=BillPayment.builder().paymentMode("Upi")
                .date(new Date().getTime())
                .amountPaid(255d).build();


        OrderDetail orderDetail= OrderDetail.builder()
                .amountToBePaid(255)
                .billPayment(billPayment)
                .foodItems("10,22,12")
                .restId(18L)
                .userId(2).build();

        orderService.addOrder(orderDetail);
    }
}