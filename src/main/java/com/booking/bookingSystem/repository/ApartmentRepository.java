package com.booking.bookingSystem.repository;

import com.booking.bookingSystem.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Optional<Apartment>findById(Long id);
    Optional<List<Apartment>> findByLocation(String location);
    Optional<List<Apartment>> findByPricePerNightBetween(BigDecimal minPrice, BigDecimal maxPrice);
    Optional<List<Apartment>> findByNumberOfRooms(int numberOfRooms);
    Optional<Apartment> findByName(String name);

}
