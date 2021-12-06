package com.gaurav.restaurantsearchservice.util;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@Builder
public class PrintOrderDetail {

    private HashMap<String,Double> foodMap;

    private String message;

}
