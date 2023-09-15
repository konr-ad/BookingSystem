package com.booking.bookingSystem.utils;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.dto.ClientDto;
import com.booking.bookingSystem.dto.OwnerDto;
import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Owner;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.repository.ApartmentRepository;
import com.booking.bookingSystem.repository.ClientRepository;
import com.booking.bookingSystem.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Utils {

    private final OwnerRepository ownerRepository;
    private final ApartmentRepository apartmentRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public Utils(OwnerRepository ownerRepository, ApartmentRepository apartmentRepository, ClientRepository clientRepository) {
        this.ownerRepository = ownerRepository;
        this.apartmentRepository = apartmentRepository;
        this.clientRepository = clientRepository;
    }

    public ReservationDto reservationToDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setApartmentId(reservation.getApartment().getId());
        dto.setClientId(reservation.getClient().getId());
        dto.setStartDate(reservation.getStartDate());
        dto.setEndDate(reservation.getEndDate());
        dto.setCreatedDate(reservation.getCreatedDate());
        return dto;
    }

    public List<ReservationDto> reservationToDto(List<Reservation> reservations) {
        List<ReservationDto> dtos = new ArrayList<>();
        ReservationDto dto = new ReservationDto();
        for (Reservation reservation : reservations) {
            dto.setId(reservation.getId());
            dto.setApartmentId(reservation.getApartment().getId());
            dto.setClientId(reservation.getClient().getId());
            dto.setStartDate(reservation.getStartDate());
            dto.setEndDate(reservation.getEndDate());
            dto.setCreatedDate(reservation.getCreatedDate());
            dtos.add(dto);
        }
        return dtos;
    }

    public Reservation reservationDtoToEntity(ReservationDto dto) {
        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setStartDate(dto.getStartDate());
        reservation.setEndDate(dto.getEndDate());
        reservation.setCreatedDate(dto.getCreatedDate());

        if (dto.getApartmentId() != null) {
            Apartment apartment = apartmentRepository.findById(dto.getApartmentId())
                    .orElseThrow(() -> new IllegalArgumentException("No Apartment found with ID: " + dto.getApartmentId()));
            reservation.setApartment(apartment);
        }

        if (dto.getClientId() != null) {
            Client client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new IllegalArgumentException("No client found with ID: " + dto.getClientId()));
            reservation.setClient(client);
        }
        return reservation;
    }

    public List<Reservation> reservationDtoToEntity(List<ReservationDto> dtos) {
        List<Reservation> reservations = new ArrayList<>();
        for (ReservationDto dto : dtos) {
            reservations.add(reservationDtoToEntity(dto));
        }
        return reservations;
    }

    public Apartment apartmentDtoToEntity(ApartmentDto dto) {
        Apartment apartment = new Apartment();
        apartment.setId(dto.getId());
        apartment.setName(dto.getName());
        apartment.setDescription(dto.getDescription());
        apartment.setAddress(dto.getAddress());
        apartment.setPricePerNight(dto.getPricePerNight());
        apartment.setNumberOfRooms(dto.getNumberOfRooms());
        apartment.setCapacity(dto.getCapacity());
        if (dto.getOwner() != null) {
            Owner owner = ownerRepository.findById(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("No owner with ID: " + dto.getId()));
            apartment.setOwner(owner);
        }
        return apartment;
    }

    public OwnerDto ownerToDto(Owner owner) {
        OwnerDto dto = new OwnerDto();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setEmail(owner.getEmail());
        dto.setPhoneNumber(owner.getPhoneNumber());
        dto.setDateOfBirth(owner.getDateofBirth());
        dto.setRegisteredDate(owner.getRegisteredDate());
        dto.setOwnedApartments(apartmentToDto(owner.getOwnedApartments()));
        return dto;
    }

    public ApartmentDto apartmentToDto(Apartment apartment) {
        ApartmentDto dto = new ApartmentDto();
        dto.setId(apartment.getId());
        dto.setName(apartment.getName());
        dto.setDescription(apartment.getDescription());
        dto.setAddress(apartment.getAddress());
        dto.setPricePerNight(apartment.getPricePerNight());
        dto.setNumberOfRooms(apartment.getNumberOfRooms());
        dto.setCapacity(apartment.getCapacity());
        dto.setOwner(ownerToDto(apartment.getOwner()));
        return dto;
    }

    public List<ApartmentDto> apartmentToDto(List<Apartment> apartments) {
        List<ApartmentDto> dtos = new ArrayList<>();
        for (Apartment apartment : apartments) {
            dtos.add(apartmentToDto(apartment));
        }
        return dtos;
    }

    public ClientDto clientToDto(Client client) {
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setFirstName(client.getFirstName());
        dto.setLastName(client.getLastName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setDateOfBirth(client.getDateofBirth());
        dto.setRegisteredDate(client.getRegisteredDate());
        return dto;
    }

    public Client ClientDtoToEntity(ClientDto dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setDateofBirth(dto.getDateOfBirth());
        client.setRegisteredDate(dto.getRegisteredDate());
        return client;
    }

    public List<ClientDto> clientToDto(List<Client> clients) {
        List<ClientDto> dtos = new ArrayList<>();
        for (Client client : clients) {
            dtos.add(clientToDto(client));
        }
        return dtos;
    }

    public Owner OwnerDtoToEntity(OwnerDto dto) {
        Owner owner = new Owner();
        owner.setId(dto.getId());
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setEmail(dto.getEmail());
        owner.setPhoneNumber(dto.getPhoneNumber());
        owner.setDateofBirth(dto.getDateOfBirth());
        owner.setRegisteredDate(dto.getRegisteredDate());
        //owner.setOwnedApartments(apartmentToDto(owner.getOwnedApartments()));
        return owner;
    }
}
