package com.gaurav.ordermanagementservice.repository;

import com.gaurav.ordermanagementservice.model.BillPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillPaymentRepository extends JpaRepository<BillPayment,Long> {

}
