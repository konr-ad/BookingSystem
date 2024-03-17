package com.booking.bookingSystem.service;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.repository.ApartmentRepository;
import com.booking.bookingSystem.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    private final ApartmentRepository apartmentRepository;
    private final ReservationService reservationService;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository, ReservationService reservationService) {
        this.apartmentRepository = apartmentRepository;
        this.reservationService = reservationService;
    }

    public Apartment findApartmentById(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartment with id: " + id + " not found"));
    }

    public ApartmentDto findApartmentDtoById(Long id) {
        Apartment apartment = findApartmentById(id);
        return DtoUtils.apartmentToDto(apartment);
    }

    public List<ApartmentDto> findByPricePerNightBetween(BigDecimal min, BigDecimal max) {
        Optional<List<Apartment>> apartments = apartmentRepository.findByPricePerNightBetween(min, max);
        if (apartments.isPresent()) return DtoUtils.apartmentToDto(apartments.get());
        throw new EntityNotFoundException("No apartments found between  price range of" + min + " - " + max);
    }

    public ApartmentDto save(ApartmentDto apartmentDto) {
        Apartment apartmentToSave = DtoUtils.apartmentDtoToEntity(apartmentDto, reservationService);
        apartmentRepository.save(apartmentToSave);
        return DtoUtils.apartmentToDto(apartmentToSave);
    }

    public void deleteById(Long id) {
        findApartmentById(id);
        apartmentRepository.deleteById(id);
    }

    public ApartmentDto update(ApartmentDto dto) {
        Apartment existingApartment = findApartmentById(dto.getId());
        updateApartmentFields(existingApartment, dto);
        Apartment savedApartment = apartmentRepository.save(existingApartment);
        return DtoUtils.apartmentToDto(savedApartment);
    }

    private void updateApartmentFields(Apartment apartment, ApartmentDto dto) {
        apartment.setId(dto.getId());
        apartment.setNumber(dto.getNumber());
        apartment.setPricePerNight(dto.getPricePerNight());
        apartment.setArea(dto.getArea());
        apartment.setCapacity(dto.getCapacity());
        if (apartment.getReservation() != null) {
            Reservation reservation = reservationService.findReservationById(dto.getId());
            apartment.setReservation(reservation);
        } else {
            apartment.setReservation(null);
        }
    }

    public List<ApartmentDto> findAll() {
        List<Apartment> listOfApartments = apartmentRepository.findAll();
        return DtoUtils.apartmentToDto(listOfApartments);
    }
}
