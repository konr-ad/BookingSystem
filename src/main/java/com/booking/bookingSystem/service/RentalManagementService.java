package com.booking.bookingSystem.service;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.enums.ReservationStatus;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.repository.ApartmentRepository;
import com.booking.bookingSystem.repository.ClientRepository;
import com.booking.bookingSystem.repository.ReservationRepository;
import com.booking.bookingSystem.utils.DtoUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalManagementService {

    private final ApartmentRepository apartmentRepository;
    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public RentalManagementService(ApartmentRepository apartmentRepository, ReservationRepository reservationRepository, ClientRepository clientRepository) {
        this.apartmentRepository = apartmentRepository;
        this.reservationRepository = reservationRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public ReservationDto createReservation(ReservationDto reservationDto) {
        Client client = clientRepository.findById(reservationDto.getClientDtoId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        List<Apartment> apartments = apartmentRepository.findAllById(reservationDto.getApartmentsDtoIds());
        if (apartments.size() != reservationDto.getApartmentsDtoIds().size()) {
            throw new EntityNotFoundException("One or more apartments not found");
        }
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setApartments(apartments);
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setTotalPrice(reservationDto.getTotalPrice());
        reservation.setNotes(reservationDto.getNotes());
        Reservation savedReservation = reservationRepository.save(reservation);
        reservationDto.setId(savedReservation.getId());
        return reservationDto;
    }

    public Reservation updateReservationFields(Reservation existingReservation, ReservationDto dto) {
        List<Apartment> apartments = apartmentRepository.findAllById(dto.getApartmentsDtoIds());
        existingReservation.setApartments(apartments);
        existingReservation.setNotes(dto.getNotes());
        existingReservation.setReservationStatus(ReservationStatus.valueOf(dto.getReservationDtoStatus()));
        existingReservation.setEndDate(dto.getEndDate());
        existingReservation.setTotalPrice(dto.getTotalPrice());
        return reservationRepository.save(existingReservation);
    }
}
