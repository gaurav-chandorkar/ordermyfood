package com.gaurav.ordermanagementservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderMsgDto {
    double amount;
    String msg;
}
