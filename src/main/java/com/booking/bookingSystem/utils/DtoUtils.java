package com.booking.bookingSystem.utils;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.dto.ClientDto;
import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.enums.ReservationStatus;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Reservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DtoUtils {

    public ReservationDto reservationToDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setApartmentId(reservation.getApartment().getId());
        dto.setClientId(reservation.getClient().getId());
        dto.setStartDate(reservation.getStartDate());
        dto.setEndDate(reservation.getEndDate());
        dto.setReservationStatus(reservation.getReservationStatus().toString());
        dto.setModifiedDate(reservation.getModifiedDate());
        dto.setTotalPrice(reservation.getTotalPrice());
        dto.setNotes(reservation.getNotes());
        return dto;
    }

    public List<ReservationDto> reservationToDto(List<Reservation> reservations) {
        List<ReservationDto> dtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dtos.add(reservationToDto(reservation));
        }
        return dtos;
    }

    public Reservation reservationDtoToEntity(ReservationDto dto, Apartment apartment, Client client) {
        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setClient(client);
        reservation.setApartment(apartment);
        reservation.setStartDate(dto.getStartDate());
        reservation.setEndDate(dto.getEndDate());
        reservation.setReservationStatus(ReservationStatus.valueOf(dto.getReservationStatus()));
        reservation.setModifiedDate(dto.getModifiedDate());
        reservation.setTotalPrice(dto.getTotalPrice());
        return reservation;
    }

    public List<Reservation> reservationDtoToEntity(List<ReservationDto> reservationsDto, Apartment apartment, Client client) {
        List<Reservation> reservations = new ArrayList<>();
        for (ReservationDto dto : reservationsDto) {
            reservations.add(reservationDtoToEntity(dto, apartment, client));
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
        apartment.setLocation(dto.getLocation());
        return apartment;
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
        dto.setLocation(apartment.getLocation());
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
        dto.setDateOfBirth(client.getDateOfBirth());
        dto.setPreferredPaymentMethod(client.getPreferredPaymentMethod());
        return dto;
    }

    public Client clientDtoToEntity(ClientDto dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setDateOfBirth(dto.getDateOfBirth());
        client.setPreferredPaymentMethod(dto.getPreferredPaymentMethod());
        return client;
    }

}
