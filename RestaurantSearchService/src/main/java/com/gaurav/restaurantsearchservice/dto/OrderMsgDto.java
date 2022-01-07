package com.gaurav.restaurantsearchservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderMsgDto {
    double amount;
    String msg;
}
