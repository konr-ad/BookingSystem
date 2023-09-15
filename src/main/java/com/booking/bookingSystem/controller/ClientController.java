package com.booking.bookingSystem.controller;

import com.booking.bookingSystem.dto.ClientDto;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.service.ClientService;
import com.booking.bookingSystem.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final Utils utils;

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService, Utils utils) {
        this.clientService = clientService;
        this.utils = utils;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            ClientDto clientDto = utils.clientToDto(client.get());
            return ResponseEntity.ok(clientDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        Client client = utils.ClientDtoToEntity(clientDto);
        Client createdClient = clientService.save(client);
        return new ResponseEntity<>(utils.clientToDto(createdClient), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            Client updatedClient = utils.ClientDtoToEntity(clientDto);
            updatedClient.setId(id);
            Client savedClient = clientService.save(updatedClient);
            return ResponseEntity.ok(utils.clientToDto(savedClient));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            clientService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
