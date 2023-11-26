package com.booking.bookingSystem.service;

import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.repository.ApartmentRepository;
import com.booking.bookingSystem.repository.ClientRepository;
import com.booking.bookingSystem.repository.ReservationRepository;
import com.booking.bookingSystem.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;
    private final ClientRepository clientRepository;

    private final DtoUtils dtoUtils;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ApartmentRepository apartmentRepository, ClientRepository clientRepository, DtoUtils dtoUtils) {
        this.reservationRepository = reservationRepository;
        this.apartmentRepository = apartmentRepository;
        this.clientRepository = clientRepository;
        this.dtoUtils = dtoUtils;
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id: " + id + " not found"));
    }

    public ReservationDto findReservationDtoById(Long id) {
        Reservation reservation = findReservationById(id);
        return dtoUtils.reservationToDto(reservation);
    }

    public ReservationDto createReservation(ReservationDto reservationDto) {
        Apartment apartment = apartmentRepository.findById(reservationDto.getApartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found"));
        Client client = clientRepository.findById(reservationDto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Reservation reservation = dtoUtils.reservationDtoToEntity(reservationDto, apartment, client);
        Reservation savedReservation = reservationRepository.save(reservation);
        return dtoUtils.reservationToDto(savedReservation);
    }

    public List<Reservation> findByClient(Client client) {
        return reservationRepository.findByClient(client);
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

    private void updateReservationFields(Reservation existingReservation, ReservationDto dto) {
        Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found"));
        existingReservation.setApartment(apartment);
        existingReservation.setNotes(dto.getNotes());
        existingReservation.setReservationStatus(dto.getReservationStatus());
        existingReservation.setEndDate(dto.getEndDate());
        existingReservation.setTotalPrice(dto.getTotalPrice());
    }
}
