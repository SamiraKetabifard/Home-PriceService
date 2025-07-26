package com.example.homepriceservice;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.List;
import com.example.homepriceservice.controller.ApartmentController;
import com.example.homepriceservice.model.ApartmentProperty;
import com.example.homepriceservice.service.ApartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ApartmentControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ApartmentService apartmentService;

    @InjectMocks
    private ApartmentController apartmentController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apartmentController).build();
    }
    // Helper method to create test apartments
    private ApartmentProperty createTestApartment(int id, int bed, float bath,
                                                  int square, String loc, double price) {
        ApartmentProperty apt = new ApartmentProperty();
        apt.setId(id);
        apt.setBedrooms(bed);
        apt.setBathrooms(bath);
        apt.setSquareFootage(square);
        apt.setLocation(loc);
        apt.setSalePrice(price);
        return apt;
    }
    @Test
    void saveAll_ShouldSaveApartments() throws Exception {
        // Given
        List<ApartmentProperty> apartments = Arrays.asList(
                createTestApartment(1, 2, 1.5f, 1000, "Downtown", 500000.0),
                createTestApartment(2, 3, 2.0f, 1500, "Suburb", 400000.0)
        );
        String jsonRequest = objectMapper.writeValueAsString(apartments);
        // When/Then
        mockMvc.perform(post("/apartment/saveAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(apartmentService, times(1)).saveAllHouse(anyList());
    }
    @Test
    void saveAll_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
        String invalidJson = "[{\"id\":1,\"bedrooms\":2,\"bathrooms\":1.5";

        mockMvc.perform(post("/apartment/saveAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    void avgAll_ShouldReturnAverage() throws Exception {
        // Given
        double expectedAvg = 450000.0;
        when(apartmentService.avgAll()).thenReturn(expectedAvg);
        // When/Then
        mockMvc.perform(get("/apartment/avgAll"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedAvg)));
    }
    @Test
    void avgAllLoc_ShouldReturnLocationAverages() throws Exception {
        // Given
        List<Double> expectedAverages = Arrays.asList(500000.0, 400000.0);
        when(apartmentService.avgAllLoc()).thenReturn(expectedAverages);
        // When/Then
        mockMvc.perform(get("/apartment/avgAllLoc"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedAverages)));
    }
    @Test
    void maxPrice_ShouldReturnMaximumPrice() throws Exception {
        // Given
        double expectedMax = 600000.0;
        when(apartmentService.maxPrice()).thenReturn(expectedMax);

        // When/Then
        mockMvc.perform(get("/apartment/maxPrice"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedMax)));
    }
    @Test
    void minPrice_ShouldReturnMinimumPrice() throws Exception {
        // Given
        double expectedMin = 300000.0;
        when(apartmentService.minPrice()).thenReturn(expectedMin);
        // When/Then
        mockMvc.perform(get("/apartment/minPrice"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedMin)));
    }
    @Test
    void avgByLoc_ShouldReturnLocationAverage() throws Exception {
        // Given
        String location = "Downtown";
        double expectedAvg = 550000.0;
        when(apartmentService.avgByLoc(location)).thenReturn(expectedAvg);
        // When/Then
        mockMvc.perform(get("/apartment/avgByLoc/{location}", location))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedAvg)));
    }
    @Test
    void avgByLoc_WithUnknownLocation_ShouldReturnZero() throws Exception {
        // Given
        String unknownLocation = "Unknown";
        when(apartmentService.avgByLoc(unknownLocation)).thenReturn(0.0);
        // When/Then
        mockMvc.perform(get("/apartment/avgByLoc/{location}", unknownLocation))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));
    }
}