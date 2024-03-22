package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.service.ApartmentService;
import com.booking.bookingSystem.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/apartments")
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

    @PostMapping
    public ResponseEntity<?> createApartment(@Valid @RequestBody ApartmentDto apartmentDto, BindingResult result) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(result);
        if (errorMap != null) return errorMap;
        ApartmentDto createdApartmentDto = apartmentService.save(apartmentDto);
        return new ResponseEntity<>(createdApartmentDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateApartment(@Valid @RequestBody ApartmentDto apartmentDto, BindingResult result) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(result);
        if (errorMap != null) return errorMap;
        ApartmentDto updatedApartmentDto = apartmentService.update(apartmentDto);
        return new ResponseEntity<>(updatedApartmentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApartment(@PathVariable Long id) {
        apartmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ApartmentDto>> searchApartments(
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(value = "minCapacity", required = false) Integer minCapacity,
            @RequestParam(value = "maxCapacity", required = false) Integer maxCapacity,
            @RequestParam(value = "area", required = false) String area) {

        List<ApartmentDto> results = apartmentService.search(minPrice, maxPrice, minCapacity, maxCapacity, area);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ApartmentDto>> getAllApartments() {
        List<ApartmentDto> listOfApartmentsDto = apartmentService.findAll();
        return new ResponseEntity<>(listOfApartmentsDto, HttpStatus.OK);
    }

}
