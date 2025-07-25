package com.example.homepriceservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "apartment")
public class ApartmentProperty {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @Column(name = "bed")
        private int bedrooms;
        @Column(name = "bath")
        private float bathrooms;
        @Column(name = "square")
        private int squareFootage;
        @Column(name = "locat")
        private String location;
        @Column(name = "price")
        private double salePrice;
    }
