package com.booking.bookingSystem.utils;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.dto.ClientDto;
import com.booking.bookingSystem.dto.OwnerDto;
import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Owner;
import com.booking.bookingSystem.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Utils {

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
        ApartmentDto dto = new ApartmentDto();
        for (Apartment apartment : apartments) {
            dto.setId(apartment.getId());
            dto.setName(apartment.getName());
            dto.setDescription(apartment.getDescription());
            dto.setAddress(apartment.getAddress());
            dto.setPricePerNight(apartment.getPricePerNight());
            dto.setNumberOfRooms(apartment.getNumberOfRooms());
            dto.setCapacity(apartment.getCapacity());
            dto.setOwner(ownerToDto(apartment.getOwner()));
            dtos.add(dto);
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
        dto.setPastReservations(reservationToDto(client.getPastReservations()));
        return dto;
    }

    public List<ClientDto> clientsToDto(List<Client> clients) {
        List<ClientDto> dtos = new ArrayList<>();
        ClientDto dto = new ClientDto();
        for (Client client : clients) {
            dto.setId(client.getId());
            dto.setFirstName(client.getFirstName());
            dto.setLastName(client.getLastName());
            dto.setEmail(client.getEmail());
            dto.setPhoneNumber(client.getPhoneNumber());
            dto.setDateOfBirth(client.getDateofBirth());
            dto.setRegisteredDate(client.getRegisteredDate());
            dto.setPastReservations(reservationToDto(client.getPastReservations()));
            dtos.add(dto);
        }
        return dtos;
    }
}
