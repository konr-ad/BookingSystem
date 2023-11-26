package com.booking.bookingSystem.service;

import com.booking.bookingSystem.dto.ClientDto;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.repository.ClientRepository;
import com.booking.bookingSystem.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final DtoUtils dtoUtils;

    @Autowired
    public ClientService(ClientRepository clientRepository, DtoUtils dtoUtils) {
        this.clientRepository = clientRepository;
        this.dtoUtils = dtoUtils;
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with id: " + id + " not found"));
    }

    public ClientDto findClientDtoById(Long id) {
        Client client = findClientById(id);
        return dtoUtils.clientToDto(client);
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

    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client existingClient = findClientById(id);
        updateClientFields(existingClient, clientDto);
        Client savedCLient = clientRepository.save(existingClient);
        return dtoUtils.clientToDto(savedCLient);
    }

    public ClientDto createClient(ClientDto clientDto) {
        Client client = dtoUtils.clientDtoToEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return dtoUtils.clientToDto(savedClient);
    }
    private void updateClientFields(Client client, ClientDto dto) {
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
    }

}
