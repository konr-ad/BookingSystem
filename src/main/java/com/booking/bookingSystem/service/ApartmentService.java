package com.booking.bookingSystem.service;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Apartment;
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
    private final DtoUtils dtoUtils;

    @Autowired
    public ApartmentService(ApartmentRepository apartmentRepository, DtoUtils dtoUtils) {
        this.apartmentRepository = apartmentRepository;
        this.dtoUtils = dtoUtils;
    }

    public Apartment findApartmentById(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartment with id: " + id + " not found"));
    }

    public ApartmentDto findApartmentDtoById(Long id) {
        Apartment apartment = findApartmentById(id);
        return dtoUtils.apartmentToDto(apartment);
    }

    public List<ApartmentDto> findbyLocation(String location) {
        List<Apartment> apartmentsDto = apartmentRepository.findByLocation(location)
                .orElseThrow(() -> new EntityNotFoundException("Apartments at location: " + location + " not found"));
        return dtoUtils.apartmentToDto(apartmentsDto);
    }

    public List<ApartmentDto> findByPricePerNightBetween(BigDecimal min, BigDecimal max) {
        Optional<List<Apartment>> apartments = apartmentRepository.findByPricePerNightBetween(min, max);
        if (apartments.isPresent()) return dtoUtils.apartmentToDto(apartments.get());
        throw new EntityNotFoundException("No apartments found between  price range of" + min + " - " + max);
    }

    public List<ApartmentDto> findByNumberOfRooms(int numberOfRooms) {
        Optional<List<Apartment>> apartments = apartmentRepository.findByNumberOfRooms(numberOfRooms);
        if (apartments.isPresent()) return dtoUtils.apartmentToDto(apartments.get());
        throw new EntityNotFoundException("No apartments found with " + numberOfRooms + " rooms");
    }

    public ApartmentDto save(ApartmentDto apartmentDto) {
        Apartment apartmentToSave = dtoUtils.apartmentDtoToEntity(apartmentDto);
        apartmentRepository.save(apartmentToSave);
        return dtoUtils.apartmentToDto(apartmentToSave);
    }

    public void deleteById(Long id) {
        findApartmentById(id);
        apartmentRepository.deleteById(id);
    }

    public ApartmentDto findbyName(String name) {
        Apartment apartment = apartmentRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Apartment with name: " + name + " not found"));
        return dtoUtils.apartmentToDto(apartment);
    }

    public ApartmentDto update(Long id, ApartmentDto dto) {
        Apartment existingApartment = findApartmentById(id);
        updateApartmentFields(existingApartment, dto);
        Apartment savedApartment = apartmentRepository.save(existingApartment);
        return dtoUtils.apartmentToDto(savedApartment);
    }

    private void updateApartmentFields(Apartment apartment, ApartmentDto dto) {
        apartment.setName(dto.getName());
        apartment.setDescription(dto.getDescription());
        apartment.setAddress(dto.getAddress());
        apartment.setPricePerNight(dto.getPricePerNight());
        apartment.setNumberOfRooms(dto.getNumberOfRooms());
        apartment.setLocation(dto.getLocation());
    }
}
