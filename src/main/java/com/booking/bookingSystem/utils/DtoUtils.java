package com.booking.bookingSystem.utils;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.dto.ClientDto;
import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.enums.ReservationStatus;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.service.ApartmentService;
import com.booking.bookingSystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoUtils {

    private final ApartmentService apartmentService;
    private final ReservationService reservationService;

    @Autowired
    public DtoUtils(ApartmentService apartmentService, ReservationService reservationService) {
        this.apartmentService = apartmentService;
        this.reservationService = reservationService;
    }

    public ReservationDto reservationToDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setApartmentsDtoIds(reservation.getApartments().stream()
                .map(Apartment::getId)
                .collect(Collectors.toList()));
        dto.setClientDtoId(reservation.getClient().getId());
        dto.setStartDate(reservation.getStartDate());
        dto.setEndDate(reservation.getEndDate());
        dto.setReservationDtoStatus(reservation.getReservationStatus().toString());
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

    public Reservation reservationDtoToEntity(ReservationDto reservationDto, ClientDto clientDto) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setClient(clientDtoToEntity(clientDto));
        reservation.setApartments((reservationDto.getApartmentsDtoIds().stream()
                .map(apartmentService::findApartmentById)
                .collect(Collectors.toList())));
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setReservationStatus(ReservationStatus.valueOf(reservationDto.getReservationDtoStatus()));
        reservation.setModifiedDate(reservationDto.getModifiedDate());
        reservation.setTotalPrice(reservationDto.getTotalPrice());
        return reservation;
    }

    public List<Reservation> reservationDtoToEntity(List<ReservationDto> reservationsDto, ClientDto clientDto) {
        List<Reservation> reservations = new ArrayList<>();
        for (ReservationDto dto : reservationsDto) {
            reservations.add(reservationDtoToEntity(dto, clientDto));
        }
        return reservations;
    }

    public Apartment apartmentDtoToEntity(ApartmentDto dto) {
        Apartment apartment = new Apartment();
        apartment.setId(dto.getId());
        apartment.setNumber(dto.getNumber());
        apartment.setPricePerNight(dto.getPricePerNight());
        apartment.setArea(dto.getArea());
        apartment.setCapacity(dto.getCapacity());
        apartment.setReservation(reservationService.findReservationById(dto.getReservationId()));
        return apartment;
    }

    public List<Apartment> apartmentDtoToEntity(List<ApartmentDto> apartmentsDto) {
        List<Apartment> apartments = new ArrayList<>();
        for (ApartmentDto dto : apartmentsDto) {
            apartments.add((apartmentDtoToEntity(dto)));
        }
        return apartments;
    }


    public ApartmentDto apartmentToDto(Apartment apartment) {
        ApartmentDto dto = new ApartmentDto();
        dto.setId(apartment.getId());
        dto.setNumber(apartment.getNumber());
        dto.setPricePerNight(apartment.getPricePerNight());
        dto.setArea(apartment.getArea());
        dto.setCapacity(apartment.getCapacity());
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
    public List<ClientDto> clientDToDto(List<Client> clients) {
        List<ClientDto> dtos = new ArrayList<>();
        for (Client client : clients) {
            dtos.add(clientToDto(client));
        }
        return dtos;
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
