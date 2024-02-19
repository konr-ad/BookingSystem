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

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with id: " + id + " not found"));
    }

    public ClientDto findClientDtoById(Long id) {
        Client client = findClientById(id);
        return DtoUtils.clientToDto(client);
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
        Client savedCLient = save(existingClient);
        return DtoUtils.clientToDto(savedCLient);
    }

    public ClientDto createClient(ClientDto clientDto) {
        Client client = DtoUtils.clientDtoToEntity(clientDto);
        Client savedClient = clientRepository.save(client);
        return DtoUtils.clientToDto(savedClient);
    }
    private void updateClientFields(Client client, ClientDto dto) {
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setPreferredPaymentMethod(dto.getPreferredPaymentMethod());
        client.setDateOfBirth(dto.getDateOfBirth());
//        if (dto.getReservationDtoId() != null) {
//
//            client.setReservation(dto.getReservationDtoId());
//        }
    }

    public List<ClientDto> findAll() {
        List<Client> clients = clientRepository.findAll();
        return DtoUtils.clientDToDto(clients);
    }
}
