package com.gaurav.restaurantsearchservice.service;

import com.gaurav.restaurantsearchservice.model.Cuisine;

import java.util.List;

public interface CuisineService {

    public List<Cuisine> getCuisineByName(String name);

    public List<Cuisine> getDistinctCuisineByName(String name);
}
