package com.booking.bookingSystem.service;

import com.booking.bookingSystem.dto.OwnerDto;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Owner;
import com.booking.bookingSystem.repository.OwnerRepository;
import com.booking.bookingSystem.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;

    private final DtoUtils utils;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, DtoUtils utils) {
        this.ownerRepository = ownerRepository;
        this.utils = utils;
    }

    public Owner findOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner with id: " + id + " not found"));
    }
    public OwnerDto findOwnerDtoById(Long id) {
        Owner owner = findOwnerById(id);
        return utils.ownerToDto(owner);
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
        findOwnerById(id);
        ownerRepository.deleteById(id);
    }

    public OwnerDto createOwner(OwnerDto dto) {
        Owner owner = utils.OwnerDtoToEntity(dto);
        Owner savedOwner = ownerRepository.save(owner);
        return utils.ownerToDto(savedOwner);
    }

    public OwnerDto updateOwner(Long id, OwnerDto dto) {
        Owner existingOwner = findOwnerById(id);
        updateOwnerFields(existingOwner, dto);
        Owner savedOwner = ownerRepository.save(existingOwner);
        return utils.ownerToDto(savedOwner);
    }

    private void updateOwnerFields(Owner owner, OwnerDto dto) {
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setEmail(dto.getEmail());
        owner.setPhoneNumber(dto.getPhoneNumber());
        owner.setDateOfBirth(dto.getDateOfBirth());
        owner.setRegisteredDate(dto.getRegisteredDate());
        // Ustaw inne pola w zależności od potrzeb
    }
}
