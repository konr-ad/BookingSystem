package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.ClientDto;
import com.booking.bookingSystem.service.ClientService;
import com.booking.bookingSystem.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    private final ClientService clientService;

    private final ValidationUtils validationUtils;

    @Autowired
    public ClientController(ClientService clientService, ValidationUtils validationUtils) {
        this.clientService = clientService;
        this.validationUtils = validationUtils;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id) {
        ClientDto clientDto = clientService.findClientDtoById(id);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> listOfClientsDto = clientService.findAll();
        return new ResponseEntity<>(listOfClientsDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientDto clientDto, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        ClientDto createdClient = clientService.createClient(clientDto);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateClient(@RequestBody ClientDto clientDto, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationUtils.getResponseEntity(bindingResult);
        if (errorMap != null) return errorMap;
        ClientDto updatedClientDto = clientService.updateClient(clientDto);
        return new ResponseEntity<>(updatedClientDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
