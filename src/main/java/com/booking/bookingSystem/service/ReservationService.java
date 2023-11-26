package com.booking.bookingSystem.service;

import com.booking.bookingSystem.dto.OwnerDto;
import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Owner;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.repository.ReservationRepository;
import com.booking.bookingSystem.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final DtoUtils dtoUtils;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, DtoUtils dtoUtils) {
        this.reservationRepository = reservationRepository;
        this.dtoUtils = dtoUtils;
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner with id: " + id + " not found"));
    }

    public ReservationDto findReservationDtoById(Long id) {
        Reservation reservation = findReservationById(id);
        return dtoUtils.reservationToDto(reservation);
    }

    public ReservationDto createReservation(ReservationDto dto) {
        Reservation reservation = dtoUtils.reservationDtoToEntity(dto);
        Reservation savedReservation = reservationRepository.save(reservation);
        return dtoUtils.reservationToDto(savedReservation);
    }

    public List<Reservation> findByClient(Client client) {
        return reservationRepository.findByClient(client);
    }

    public List<Reservation> findByApartment(Apartment apartament){
        return reservationRepository.findByApartment(apartament);
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteById(Long id) {
        reservationRepository.findById(id);
        reservationRepository.deleteById(id);
    }

    public ReservationDto updateReservation(Long id, ReservationDto dto) {
        Reservation existingReservation = findReservationById(id);
        updateReservationFields(existingReservation, dto);
        Reservation updatedReservation = save(existingReservation);
        return dtoUtils.reservationToDto(updatedReservation);
    }

    private void updateReservationFields(Reservation reservation, ReservationDto dto) {
        reservation.setApartment(dto.getApartmentId());
        reservation.setLastName(dto.getLastName());
        reservation.setEmail(dto.getEmail());
        reservation.setPhoneNumber(dto.getPhoneNumber());
        reservation.setDateOfBirth(dto.getDateOfBirth());
        reservation.setRegisteredDate(dto.getRegisteredDate());
    }
}
