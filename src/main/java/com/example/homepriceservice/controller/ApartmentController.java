package com.example.homepriceservice.controller;

import com.example.homepriceservice.model.ApartmentProperty;
import com.example.homepriceservice.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("apartment")
public class ApartmentController {

        @Autowired
        ApartmentService apartmentService;

        @PostMapping("saveAll")
        public void saveAll(@RequestBody List<ApartmentProperty> apartment){
            apartmentService.saveAllHouse(apartment);
        }

        @GetMapping("avgAll")
        public double avgAll(){
            return apartmentService.avgAll();
        }

        @GetMapping("avgAllLoc")
        public List<Double> avgAllLoc(){
            return apartmentService.avgAllLoc();
        }

        @GetMapping("maxPrice")
        public double maxPrice(){
            return apartmentService.maxPrice();
        }

        @GetMapping("minPrice")
        public double minPrice(){
            return apartmentService.minPrice();
        }

        @GetMapping("avgByLoc/{location}")
        public double avgByLoc(@PathVariable String location){
            return apartmentService.avgByLoc(location);
        }
    }

