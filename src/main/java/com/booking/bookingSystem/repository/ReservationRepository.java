package com.booking.bookingSystem.repository;

import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByClient(Client client);
    List<Reservation> findByApartment(Apartment apartament);
    Optional<Reservation> findById(Long id);

}
