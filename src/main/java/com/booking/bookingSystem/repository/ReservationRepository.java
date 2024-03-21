package com.booking.bookingSystem.repository;

import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

    List<Reservation> findByClient(Client client);
    Optional<Reservation> findById(Long id);

}
