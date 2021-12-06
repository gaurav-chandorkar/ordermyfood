package com.gaurav.restaurantsearchservice.model;

import lombok.*;

import javax.persistence.*;

//@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long orderId;

   long restId;

    long userId;

    double amountToBePaid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentID",
            referencedColumnName = "paymentID")
    BillPayment billPayment;

    String foodItems;
}
