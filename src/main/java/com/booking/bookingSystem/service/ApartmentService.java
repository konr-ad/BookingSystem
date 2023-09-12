package com.booking.bookingSystem.service;

import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Owner;
import com.booking.bookingSystem.repository.ApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public Optional<Apartment> findById(Long id) {
        return apartmentRepository.findById(id);
    }

    public List<Apartment> findbyLocation(String location) {
        return apartmentRepository.findByLocation(location);
    }

    public List<Apartment> findByPricePerNightBetween(BigDecimal min, BigDecimal max) {
        return apartmentRepository.findByPricePerNightBetween(min, max);
    }

    public List<Apartment> findByNumerOfRooms(int numberOfRooms) {
        return apartmentRepository.findByNumberOfRooms(numberOfRooms);
    }

    public List<Apartment> findByOwner(Owner owner) {
        return apartmentRepository.findByOwner(owner);
    }

    public Apartment save(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    public void deleteById(Long id) {
        apartmentRepository.deleteById(id);
    }
}
