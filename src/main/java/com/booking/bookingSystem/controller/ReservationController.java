package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.service.ReservationService;
import com.booking.bookingSystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final Utils utils;
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService, Utils utils) {
        this.reservationService = reservationService;
        this.utils = utils;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.findById(id);
        if(reservation.isPresent()) {
            ReservationDto dto = utils.reservationToDto(reservation.get());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation reservation = utils.reservationDtoToEntity(reservationDto);
        Reservation createdReservation = reservationService.save(reservation);
        return new ResponseEntity<>(utils.reservationToDto(createdReservation), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {
        Optional<Reservation> reservation = reservationService.findById(id);
        if(reservation.isPresent()) {
            Reservation updatedReservation = utils.reservationDtoToEntity(reservationDto);
            updatedReservation.setId(id);
            Reservation savedReservation = reservationService.save(updatedReservation);
            return ResponseEntity.ok(utils.reservationToDto(savedReservation));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.findById(id);
        if(reservation.isPresent()) {
            reservationService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
