package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.service.ReservationService;
import com.booking.bookingSystem.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ValidationUtils validationUtils;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService, ValidationUtils validationUtils) {
        this.reservationService = reservationService;
        this.validationUtils = validationUtils;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) {
        ReservationDto reservationDto = reservationService.findReservationDtoById(id);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody ReservationDto reservationDto, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        ReservationDto createdReservationDto = reservationService.createReservation(reservationDto);
        return new ResponseEntity<>(createdReservationDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @Valid @RequestBody ReservationDto reservationDto, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        ReservationDto updatedReservationDto = reservationService.updateReservation(id, reservationDto);
        return new ResponseEntity<>(updatedReservationDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
