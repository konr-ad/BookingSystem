package com.booking.bookingSystem.repository;

import com.booking.bookingSystem.model.DigitalKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigitalKeyRepository extends JpaRepository<DigitalKey, Long> {
}
