package com.example.homepriceservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import com.example.homepriceservice.model.ApartmentProperty;
import com.example.homepriceservice.repository.ApartmentRepository;
import com.example.homepriceservice.service.ApartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ApartmentServiceTest {

    @Mock
    private ApartmentRepository apartmentRepository;

    @InjectMocks
    private ApartmentService apartmentService;

    private List<ApartmentProperty> testApartments;

    @BeforeEach
    void setUp() {
        testApartments = Arrays.asList(
                new ApartmentProperty(1, 2, 1.5f, 1000, "Downtown", 500000.0),
                new ApartmentProperty(2, 3, 2.0f, 1500, "Suburb", 400000.0)
        );
    }
    // Happy path tests
    @Test
    void saveAllHouse_ShouldCallRepositorySaveAll() {
        apartmentService.saveAllHouse(testApartments);
        verify(apartmentRepository, times(1)).saveAll(testApartments);
    }
    @Test
    void avgAll_ShouldReturnAveragePrice() {
        when(apartmentRepository.averageAll()).thenReturn(450000.0);
        double result = apartmentService.avgAll();
        assertEquals(450000.0, result);
    }
    @Test
    void avgAllLoc_ShouldReturnAveragePricesByLocation() {
        List<Double> expected = Arrays.asList(500000.0, 400000.0);
        when(apartmentRepository.averageAllLocation()).thenReturn(expected);
        List<Double> result = apartmentService.avgAllLoc();
        assertEquals(expected, result);
    }
    @Test
    void maxPrice_ShouldReturnMaximumPrice() {
        when(apartmentRepository.maxPrice()).thenReturn(500000.0);
        double result = apartmentService.maxPrice();
        assertEquals(500000.0, result);
    }
    @Test
    void minPrice_ShouldReturnMinimumPrice() {
        when(apartmentRepository.minPrice()).thenReturn(400000.0);
        double result = apartmentService.minPrice();
        assertEquals(400000.0, result);
    }
    @Test
    void avgByLoc_ShouldReturnAverageForLocation() {
        when(apartmentRepository.avgByLocation("Downtown")).thenReturn(500000.0);
        double result = apartmentService.avgByLoc("Downtown");
        assertEquals(500000.0, result);
    }
}
