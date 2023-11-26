package com.booking.bookingSystem.utils;

import com.booking.bookingSystem.repository.ApartmentRepository;
import com.booking.bookingSystem.repository.ClientRepository;
import com.booking.bookingSystem.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationUtils {
    private final OwnerRepository ownerRepository;
    private final ApartmentRepository apartmentRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ValidationUtils(OwnerRepository ownerRepository, ApartmentRepository apartmentRepository, ClientRepository clientRepository) {
        this.ownerRepository = ownerRepository;
        this.apartmentRepository = apartmentRepository;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<?> getResponseEntity(BindingResult result) {
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
