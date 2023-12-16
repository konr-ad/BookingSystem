package com.booking.bookingSystem.service;

import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.enums.ReservationStatus;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.DigitalKey;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.qrCode.QrCodeGenerator;
import com.booking.bookingSystem.repository.ApartmentRepository;
import com.booking.bookingSystem.repository.ClientRepository;
import com.booking.bookingSystem.repository.DigitalKeyRepository;
import com.booking.bookingSystem.repository.ReservationRepository;
import com.booking.bookingSystem.utils.DtoUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApartmentRepository apartmentRepository;
    private final ClientRepository clientRepository;
    private final DigitalKeyRepository digitalKeyRepository;
    private final DtoUtils dtoUtils;
    private final QrCodeGenerator qrCodeGenerator;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ApartmentRepository apartmentRepository, ClientRepository clientRepository, DtoUtils dtoUtils, DigitalKeyRepository digitalKeyRepository, QrCodeGenerator qrCodeGenerator) {
        this.reservationRepository = reservationRepository;
        this.apartmentRepository = apartmentRepository;
        this.clientRepository = clientRepository;
        this.dtoUtils = dtoUtils;
        this.digitalKeyRepository = digitalKeyRepository;
        this.qrCodeGenerator = qrCodeGenerator;
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id: " + id + " not found"));
    }

    public ReservationDto findReservationDtoById(Long id) {
        Reservation reservation = findReservationById(id);
        return dtoUtils.reservationToDto(reservation);
    }
    @Transactional
    public ReservationDto createReservation(ReservationDto reservationDto) throws Exception {
        Apartment apartment = apartmentRepository.findById(reservationDto.getApartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found"));
        Client client = clientRepository.findById(reservationDto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Reservation reservation = dtoUtils.reservationDtoToEntity(reservationDto, apartment, client);
        DigitalKey digitalKey = generateDigitalKeyForReservation(reservation);
        Reservation savedReservation = reservationRepository.save(reservation);
        savedReservation.setDigitalKey(digitalKey);
        Reservation saveReservationWithADigitalKey = reservationRepository.save(savedReservation);
        return dtoUtils.reservationToDto(saveReservationWithADigitalKey);
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
//        existingReservation.setApartment(apartment);
        existingReservation.setNotes(dto.getNotes());
        existingReservation.setReservationStatus(ReservationStatus.valueOf(dto.getReservationStatus()));
        existingReservation.setEndDate(dto.getEndDate());
        existingReservation.setTotalPrice(dto.getTotalPrice());
    }

    private DigitalKey generateDigitalKeyForReservation(Reservation reservation) throws Exception {
        DigitalKey digitalKey = new DigitalKey();
        String qrCodeData = "Dane QR - np. ID rezerwacji: " + reservation.getId();
        String qrCodeImage = qrCodeGenerator.generateQRCodeImage(qrCodeData);
        digitalKey.setQrCode(qrCodeImage);
        digitalKey.setValidFrom(reservation.getStartDate().atTime(15, 0));
        digitalKey.setValidUntil(reservation.getEndDate().atTime(11, 0));
        digitalKey.setReservation(reservation);
        return digitalKeyRepository.save(digitalKey);
    }
}
