package com.booking.bookingSystem.repository;

import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findById(Long id);
    Optional<Client> findByEmail(String email);
    List<Client> findByLastName(String lastName);
    Optional<Client> findByPhoneNumber(String phoneNumber);
}
