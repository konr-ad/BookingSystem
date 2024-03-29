package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.service.ApartmentService;
import com.booking.bookingSystem.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/apartments")
public class ApartmentController {
    private final ValidationUtils validationUtils;
    private final ApartmentService apartmentService;

    @Autowired
    public ApartmentController(ApartmentService apartmentService, ValidationUtils validationUtils) {
        this.apartmentService = apartmentService;
        this.validationUtils = validationUtils;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApartment(@PathVariable Long id) {
        ApartmentDto apartmentDto = apartmentService.findApartmentDtoById(id);
        return new ResponseEntity<>(apartmentDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getApartmentByName(@Valid @RequestParam String name, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        ApartmentDto apartmentDto = apartmentService.findbyName(name);
        return new ResponseEntity<>(apartmentDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getApartmentByLocation(@Valid @RequestParam String location, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        List<ApartmentDto> apartmentsDto = apartmentService.findbyLocation(location);
        return new ResponseEntity<>(apartmentsDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createApartment(@Valid @RequestBody ApartmentDto apartmentDto, BindingResult result) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(result);
        if (errorMap != null) return errorMap;
        ApartmentDto createdApartmentDto = apartmentService.save(apartmentDto);
        return new ResponseEntity<>(createdApartmentDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApartment(@PathVariable Long id, @Valid @RequestBody ApartmentDto apartmentDto, BindingResult result) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(result);
        if (errorMap != null) return errorMap;
        ApartmentDto updatedApartmentDto = apartmentService.update(id, apartmentDto);
        return new ResponseEntity<>(updatedApartmentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        apartmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
