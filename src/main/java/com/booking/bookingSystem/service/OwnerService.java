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

    private final DtoUtils dtoUtils;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, DtoUtils dtoUtils) {
        this.ownerRepository = ownerRepository;
        this.dtoUtils = dtoUtils;
    }

    public Owner findOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner with id: " + id + " not found"));
    }
    public OwnerDto findOwnerDtoById(Long id) {
        Owner owner = findOwnerById(id);
        return dtoUtils.ownerToDto(owner);
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
        Owner owner = dtoUtils.ownerDtoToEntity(dto);
        Owner savedOwner = ownerRepository.save(owner);
        return dtoUtils.ownerToDto(savedOwner);
    }

    public OwnerDto updateOwner(Long id, OwnerDto dto) {
        Owner existingOwner = findOwnerById(id);
        updateOwnerFields(existingOwner, dto);
        Owner updatedOwner = ownerRepository.save(existingOwner);
        return dtoUtils.ownerToDto(updatedOwner);
    }

    private void updateOwnerFields(Owner owner, OwnerDto dto) {
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setEmail(dto.getEmail());
        owner.setPhoneNumber(dto.getPhoneNumber());
        owner.setDateOfBirth(dto.getDateOfBirth());
        owner.setRegisteredDate(dto.getRegisteredDate());
    }
}
