package com.gaurav.restaurantsearchservice.service.impl;

import com.gaurav.restaurantsearchservice.model.Cuisine;
import com.gaurav.restaurantsearchservice.repository.CuisineRepository;
import com.gaurav.restaurantsearchservice.service.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuisineServiceImpl implements CuisineService {

    CuisineRepository cuisineRepository;

    @Autowired
    CuisineServiceImpl(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }

    @Override
    public List<Cuisine> getCuisineByName(String name) {

        return cuisineRepository.findDistinctByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Cuisine> getDistinctCuisineByName(String name) {

        return cuisineRepository.findDistinctByName(name);
    }
}
