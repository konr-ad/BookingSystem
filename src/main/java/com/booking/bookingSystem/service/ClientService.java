package com.booking.bookingSystem.service;

import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService (ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    List<Client> findByLastName(String lastName) {
        return clientRepository.findByLastName(lastName);
    }

    Optional<Client> findByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
