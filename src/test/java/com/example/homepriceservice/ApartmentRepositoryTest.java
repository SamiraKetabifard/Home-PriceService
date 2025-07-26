package com.example.homepriceservice;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import com.example.homepriceservice.model.ApartmentProperty;
import com.example.homepriceservice.repository.ApartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ApartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @BeforeEach
    void setUp() {
        // Clear any existing data before each test
        apartmentRepository.deleteAll();
    }
    @Test
    void averageAll_ShouldReturnCorrectAverage() {
        // Given
        createApartment(2, 1.5f, 1000, "Downtown", 500000.0);
        createApartment(3, 2.0f, 1500, "Suburb", 400000.0);
        // When
        Double average = apartmentRepository.averageAll();
        // Then
        assertThat(average).isEqualTo(450000.0);
    }
    @Test
    void averageAll_WhenNoData_ShouldReturnNull() {
        // When
        Double average = apartmentRepository.averageAll();
        // Then
        assertThat(average).isNull();
    }
    @Test
    void averageAllLocation_ShouldReturnAveragesByLocation() {
        // Given
        createApartment(2, 1.5f, 1000, "Downtown", 500000.0);
        createApartment(3, 2.0f, 1500, "Suburb", 400000.0);
        createApartment(2, 1.0f, 800, "Downtown", 600000.0);
        // When
        List<Double> averages = apartmentRepository.averageAllLocation();
        // Then
        assertThat(averages).hasSize(2)
                .containsExactlyInAnyOrder(550000.0, 400000.0);
    }
    @Test
    void avgByLocation_ShouldReturnCorrectAverageForLocation() {
        // Given
        createApartment(2, 1.5f, 1000, "Downtown", 500000.0);
        createApartment(3, 2.0f, 1500, "Suburb", 400000.0);
        createApartment(2, 1.0f, 800, "Downtown", 600000.0);
        // When
        Double average = apartmentRepository.avgByLocation("Downtown");
        // Then
        assertThat(average).isEqualTo(550000.0);
    }
    @Test
    void avgByLocation_WhenLocationNotFound_ShouldReturnNull() {
        // When
        Double average = apartmentRepository.avgByLocation("Unknown");
        // Then
        assertThat(average).isNull();
    }
    @Test
    void maxPrice_ShouldReturnHighestPrice() {
        // Given
        createApartment(2, 1.5f, 1000, "Downtown", 500000.0);
        createApartment(3, 2.0f, 1500, "Suburb", 400000.0);
        createApartment(2, 1.0f, 800, "Downtown", 600000.0);
        // When
        Double maxPrice = apartmentRepository.maxPrice();
        // Then
        assertThat(maxPrice).isEqualTo(600000.0);
    }
    @Test
    void maxPrice_WhenNoData_ShouldReturnNull() {
        // When
        Double maxPrice = apartmentRepository.maxPrice();
        // Then
        assertThat(maxPrice).isNull();
    }
    @Test
    void minPrice_ShouldReturnLowestPrice() {
        // Given
        createApartment(2, 1.5f, 1000, "Downtown", 500000.0);
        createApartment(3, 2.0f, 1500, "Suburb", 400000.0);
        createApartment(2, 1.0f, 800, "Downtown", 600000.0);
        // When
        Double minPrice = apartmentRepository.minPrice();
        // Then
        assertThat(minPrice).isEqualTo(400000.0);
    }
    @Test
    void minPrice_WhenNoData_ShouldReturnNull() {
        // When
        Double minPrice = apartmentRepository.minPrice();
        // Then
        assertThat(minPrice).isNull();
    }
    private ApartmentProperty createApartment(int bedrooms, float bathrooms,
                                              int squareFootage, String location, double price) {
        ApartmentProperty apartment = new ApartmentProperty();
        apartment.setBedrooms(bedrooms);
        apartment.setBathrooms(bathrooms);
        apartment.setSquareFootage(squareFootage);
        apartment.setLocation(location);
        apartment.setSalePrice(price);
        return entityManager.persistAndFlush(apartment);
    }
}