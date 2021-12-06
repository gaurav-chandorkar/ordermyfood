package com.gaurav.ordermanagementservice;

import com.gaurav.ordermanagementservice.model.BillPayment;
import com.gaurav.ordermanagementservice.model.OrderDetail;
import com.gaurav.ordermanagementservice.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class OrderManagementServiceApplication implements CommandLineRunner {

    @Autowired
    OrderServiceImpl orderService;

    public static void main(String[] args) {
        SpringApplication.run(OrderManagementServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
    public static OrderDetail getDemoOrderDetail(){

        BillPayment billPayment=BillPayment.builder().paymentMode("Upi")
                .date(new Date().getTime())
                .amountPaid(255d).build();


        OrderDetail orderDetail= OrderDetail.builder()
                .amountToBePaid(255)
                .billPayment(billPayment)
                .foodItems("10,22,12")
                .restId(18L)
                .userId(2).build();

        return orderDetail;
    }
}
