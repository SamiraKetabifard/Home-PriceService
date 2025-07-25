package com.example.homepriceservice.service;

import com.example.homepriceservice.model.ApartmentProperty;
import com.example.homepriceservice.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApartmentService {

        @Autowired
        ApartmentRepository apartmentRepository;

        public void saveAllHouse(List<ApartmentProperty> apartment){
            apartmentRepository.saveAll(apartment);
        }

        public double avgAll() {
            return apartmentRepository.averageAll();
        }

        public List<Double> avgAllLoc() {
            return apartmentRepository.averageAllLocation();
        }

        public double maxPrice() {
            return apartmentRepository.maxPrice();
        }

        public double minPrice() {
            return apartmentRepository.minPrice();
        }

        public double avgByLoc(String location) {
            return apartmentRepository.avgByLocation(location);
        }
    }
