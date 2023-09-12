package com.booking.bookingSystem.service;

import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Owner;
import com.booking.bookingSystem.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService (OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Optional<Owner> findById(Long id) {
        return ownerRepository.findById(id);
    }

    public Optional<Owner> findByEmail(String email) {
        return ownerRepository.findByEmail(email);
    }

    List<Owner> findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    Optional<Owner> findByPhoneNumber(String phoneNumber) {
        return ownerRepository.findByPhoneNumber(phoneNumber);
    }

    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }
}
