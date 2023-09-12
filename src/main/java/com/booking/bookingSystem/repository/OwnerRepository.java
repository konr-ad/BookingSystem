package com.booking.bookingSystem.repository;

import com.booking.bookingSystem.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findById(Long id);
    Optional<Owner> findByEmail(String email);
    List<Owner> findByLastName(String lastName);
    Optional<Owner> findByPhoneNumber(String phoneNumber);

}
