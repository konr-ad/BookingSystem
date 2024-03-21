package com.booking.bookingSystem.service;

import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.enums.ReservationStatus;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.qrCode.QrCodeGenerator;
import com.booking.bookingSystem.repository.ApartmentRepository;
import com.booking.bookingSystem.repository.DigitalKeyRepository;
import com.booking.bookingSystem.repository.ReservationRepository;
import com.booking.bookingSystem.repository.specification.ReservationSpecification;
import com.booking.bookingSystem.utils.DtoUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;
    private final RentalManagementService rentalManagementService;
    private final DigitalKeyRepository digitalKeyRepository;
    private final QrCodeGenerator qrCodeGenerator;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ApartmentRepository apartmentRepository, DigitalKeyRepository digitalKeyRepository, QrCodeGenerator qrCodeGenerator, RentalManagementService rentalManagementService) {
        this.reservationRepository = reservationRepository;
        this.apartmentRepository = apartmentRepository;
        this.rentalManagementService = rentalManagementService;
        this.digitalKeyRepository = digitalKeyRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id: " + id + " not found"));
    }

    public List<Reservation> findReservationsById(List<Long> ids) {
        return reservationRepository.findAllById(ids);
    }

    public ReservationDto findReservationDtoById(Long id) {
        Reservation reservation = findReservationById(id);
        return DtoUtils.reservationToDto(reservation);
    }

    @Transactional
    public ReservationDto createReservation(ReservationDto reservationDto) {
        return rentalManagementService.createReservation(reservationDto);
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

    public ReservationDto updateReservation(ReservationDto dto) {
        Reservation existingReservation = findReservationById(dto.getId());
        Reservation updatedReservation = rentalManagementService.updateReservationFields(existingReservation, dto);
        return DtoUtils.reservationToDto(updatedReservation);
    }

    public List<ReservationDto> search(String lastName, String phoneNumber, ReservationStatus status) {
        Specification<Reservation> spec = Specification.where(ReservationSpecification.hasClientLastName(lastName))
                .and(ReservationSpecification.hasClientPhoneNumber(phoneNumber))
                .and(ReservationSpecification.hasReservationStatus(status));

        List<Reservation> results = reservationRepository.findAll(spec);
        return results.stream().map(DtoUtils::reservationToDto).collect(Collectors.toList());
    }
}
