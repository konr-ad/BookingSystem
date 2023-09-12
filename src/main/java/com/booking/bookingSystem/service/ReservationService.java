package com.booking.bookingSystem.service;

import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
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
        reservationRepository.deleteById(id);
    }


}
