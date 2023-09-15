package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.ClientDto;
import com.booking.bookingSystem.dto.OwnerDto;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Owner;
import com.booking.bookingSystem.service.OwnerService;
import com.booking.bookingSystem.utils.Utils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final Utils utils;

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService, Utils utils) {
        this.ownerService = ownerService;
        this.utils = utils;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable Long id) {
        Optional<Owner> owner = ownerService.findById(id);
        if (owner.isPresent()) {
            OwnerDto ownerDto = utils.ownerToDto(owner.get());
            return ResponseEntity.ok(ownerDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = utils.OwnerDtoToEntity(ownerDto);
        Owner createdOwner = ownerService.save(owner);
        return new ResponseEntity<>(utils.ownerToDto(createdOwner), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable Long id, @RequestBody OwnerDto ownerDto) {
        Optional<Owner> owner = ownerService.findById(id);
        if (owner.isPresent()) {
            Owner updatedOwner = utils.OwnerDtoToEntity(ownerDto);
            updatedOwner.setId(id);
            Owner savedOwner = ownerService.save(updatedOwner);
            return ResponseEntity.ok(utils.ownerToDto(savedOwner));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        Optional<Owner> owner = ownerService.findById(id);
        if (owner.isPresent()) {
            ownerService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
