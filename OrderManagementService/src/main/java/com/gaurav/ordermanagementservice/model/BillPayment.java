package com.gaurav.ordermanagementservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long paymentID;

    @Column(name = "payment_mode")
    String paymentMode;

    double amountPaid;
    long date;


}
