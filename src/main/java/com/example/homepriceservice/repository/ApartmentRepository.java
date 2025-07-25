package com.example.homepriceservice.repository;

import com.example.homepriceservice.model.ApartmentProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<ApartmentProperty, Integer> {
    @Query(value = "select avg(price) from apartment" , nativeQuery = true)
    Double averageAll();

    @Query(value = "select avg(price) from apartment group by locat" , nativeQuery = true)
    List<Double> averageAllLocation();

    @Query(value = "select max(price) from apartment", nativeQuery = true)
    Double maxPrice();

    @Query(value = "select min(price) from apartment", nativeQuery = true)
    Double minPrice();

    @Query(value = "select avg(price) from apartment where locat = ?1", nativeQuery = true)
    Double avgByLocation(String location);
}
