package com.booking.bookingSystem.repository;

import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {

    Optional<Apartment>findById(Long id);
    List<Apartment> findByLocation(String location);
    List<Apartment> findByPricePerNightBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Apartment> findByNumberOfRooms(int numberOfRooms);
    List<Apartment> findByOwner(Owner owner);

}
