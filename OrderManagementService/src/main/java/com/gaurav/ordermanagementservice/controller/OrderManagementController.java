package com.gaurav.ordermanagementservice.controller;

import com.gaurav.ordermanagementservice.OrderManagementServiceApplication;
import com.gaurav.ordermanagementservice.Test;
import com.gaurav.ordermanagementservice.dto.OrderMsgDto;
import com.gaurav.ordermanagementservice.model.BillPayment;
import com.gaurav.ordermanagementservice.model.OrderDetail;
import com.gaurav.ordermanagementservice.service.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/order")
public class OrderManagementController {

    @Autowired
    OrderServiceImpl orderService;

    //@Autowired
    //private KafkaTemplate<String,Object> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger(OrderManagementController.class);

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public OrderDetail test() {

        return OrderManagementServiceApplication.getDemoOrderDetail();
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Test placeOrderold(@RequestBody String str) {
        Test test = new Test();
        test.str = test.str.concat(" " + str);
        return test;
    }

    @RequestMapping(value = "/place-order/{paymentMode}", method = RequestMethod.POST)
    public boolean placeOrder(@RequestBody OrderDetail orderDetail, @PathVariable String paymentMode) {
        String[] items = orderDetail.getFoodItems().trim().split(",");
        logger.info("grv items.length {}, orderDetail.getFoodItems {}", items.length, orderDetail.getFoodItems());
        if (orderDetail.getFoodItems().trim().length() > 1 && items.length > 0) {
            boolean isPaymentSuccess = processPayment(orderDetail, paymentMode);
            if (!isPaymentSuccess)
                return false;
            logger.info("is payment success: "+isPaymentSuccess);
            orderService.addOrder(orderDetail);
            //kafkaTemplate.send("test-topic", "kafka, Order placed successfully");
            //OrderMsgDto orderMsgDto=OrderMsgDto.builder().msg("Order has been placed successfully")
              //      .amount(orderDetail.getAmountToBePaid()).build();
           // kafkaTemplate.send("test-topic",(OrderMsgDto)orderMsgDto );
            return true;
        } else
            return false;

    }

    @RequestMapping(value = "/get-order-detail/{orderId}", method = RequestMethod.GET)
    public OrderDetail getOrderById(@PathVariable long orderId) {
        logger.info("get-order-detail called");
        return orderService.displayOrder(orderId, 2L);
    }

    @RequestMapping(value = "/cancel-order/{orderId}", method = RequestMethod.POST)
    public boolean cancelOrder(@PathVariable long orderId) {
        logger.info("cancelOrder called");
        return orderService.cancelOrder(orderId);

    }


    private boolean processPayment(OrderDetail orderDetail, String paymentMode) {
        String[] paymentModes = {"Cash", "Upi", "Credit Card", "Debit Card"};
        Random random = new Random();
        int num = random.nextInt(10);
        if (num < 9 || paymentMode.equals(paymentModes[0])) {
            BillPayment billPayment = BillPayment.builder().paymentMode(paymentMode)
                    .amountPaid(orderDetail.getAmountToBePaid())
                    .date(new Date().getTime()).build();
            orderDetail.setBillPayment(billPayment);
            return true;
        }

        return false;
    }
}
