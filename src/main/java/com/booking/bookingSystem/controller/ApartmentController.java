package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.service.ApartmentService;
import com.booking.bookingSystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/apartments")
public class ApartmentController {
    private final Utils utils;
    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService, Utils utils) {
        this.apartmentService = apartmentService;
        this.utils = utils;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApartmentDto> getApartment(@PathVariable Long id) {
        Optional<Apartment> apartment = apartmentService.findById(id);
        if (apartment.isPresent()) {
            ApartmentDto dto = utils.apartmentToDto(apartment.get());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<ApartmentDto> getApartmentByName(@PathVariable String name) {
        Optional<Apartment> apartment = apartmentService.findbyName(name);
        if (apartment.isPresent()) {
            ApartmentDto dto = utils.apartmentToDto(apartment.get());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/bylocation/{location}")
    public ResponseEntity<ApartmentDto> getApartmentByLocation(@PathVariable String location) {
        Optional<Apartment> apartment = apartmentService.findbyLocation(location);
        if (apartment.isPresent()) {
            ApartmentDto dto = utils.apartmentToDto(apartment.get());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ApartmentDto> createApartment(@RequestBody ApartmentDto apartmentDto) {
        Apartment apartment = utils.apartmentDtoToEntity(apartmentDto);
        Apartment createdApartment = apartmentService.save(apartment);
        return new ResponseEntity<>(utils.apartmentToDto(createdApartment), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApartmentDto> updateApartment(@PathVariable Long id, @RequestBody ApartmentDto apartmentDto) {
        Optional<Apartment> apartment = apartmentService.findById(id);
        if (apartment.isPresent()) {
            Apartment updatedApartment = utils.apartmentDtoToEntity(apartmentDto);
            updatedApartment.setId(id);
            Apartment savedApartment = apartmentService.save(updatedApartment);
            return ResponseEntity.ok(utils.apartmentToDto(savedApartment));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        Optional<Apartment> apartment = apartmentService.findById(id);
        if (apartment.isPresent()) {
            apartmentService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
