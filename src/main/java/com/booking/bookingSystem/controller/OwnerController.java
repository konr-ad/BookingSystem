package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.OwnerDto;
import com.booking.bookingSystem.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/owners")
public class OwnerController {


    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDto> getOwner(@PathVariable Long id) {
        OwnerDto ownerDto = ownerService.findOwnerDtoById(id);
        return new ResponseEntity<>(ownerDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOwner(@Valid @RequestBody OwnerDto ownerDto, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        OwnerDto createdOwnerDto = ownerService.createOwner(ownerDto);
        return new ResponseEntity<>(createdOwnerDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable Long id, @Valid @RequestBody OwnerDto ownerDto, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        OwnerDto updatedOwnerDto = ownerService.updateOwner(id, ownerDto);
        return new ResponseEntity<>(updatedOwnerDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        ownerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<?> getResponseEntity(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
