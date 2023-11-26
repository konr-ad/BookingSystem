package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.OwnerDto;
import com.booking.bookingSystem.service.OwnerService;
import com.booking.bookingSystem.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/owners")
public class OwnerController {


    private final OwnerService ownerService;
    private final ValidationUtils validationUtils;

    @Autowired
    public OwnerController(OwnerService ownerService, ValidationUtils validationUtils) {
        this.ownerService = ownerService;
        this.validationUtils = validationUtils;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable Long id) {
        OwnerDto ownerDto = ownerService.findOwnerDtoById(id);
        return new ResponseEntity<>(ownerDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOwner(@Valid @RequestBody OwnerDto ownerDto, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        OwnerDto createdOwnerDto = ownerService.createOwner(ownerDto);
        return new ResponseEntity<>(createdOwnerDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerDto ownerDto, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        OwnerDto updatedOwnerDto = ownerService.updateOwner(id, ownerDto);
        return new ResponseEntity<>(updatedOwnerDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable Long id) {
        ownerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
