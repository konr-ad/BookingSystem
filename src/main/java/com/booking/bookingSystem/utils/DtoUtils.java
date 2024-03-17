package com.booking.bookingSystem.utils;

import com.booking.bookingSystem.dto.ApartmentDto;
import com.booking.bookingSystem.dto.ClientDto;
import com.booking.bookingSystem.dto.ReservationDto;
import com.booking.bookingSystem.enums.ReservationStatus;
import com.booking.bookingSystem.exception.EntityNotFoundException;
import com.booking.bookingSystem.model.Apartment;
import com.booking.bookingSystem.model.Client;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.repository.ApartmentRepository;
import com.booking.bookingSystem.repository.ClientRepository;
import com.booking.bookingSystem.service.ReservationService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoUtils {

    public static ReservationDto reservationToDto(Reservation reservation) {
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

    public static List<ReservationDto> reservationToDto(List<Reservation> reservations) {
        List<ReservationDto> dtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            dtos.add(reservationToDto(reservation));
        }
        return dtos;
    }

    public Reservation reservationDtoToEntity(ReservationDto reservationDto, ClientRepository clientRepository, ApartmentRepository apartmentRepository) {
        Reservation reservation = new Reservation();
        Client client = clientRepository.findById(reservationDto.getClientDtoId())
                .orElseThrow(() -> new EntityNotFoundException("Client not found for ID: " + reservationDto.getClientDtoId()));
        List<Apartment> apartments = apartmentRepository.findAllById(reservationDto.getApartmentsDtoIds());

        reservation.setClient(client);
        reservation.setApartments(apartments);
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setEndDate(reservationDto.getEndDate());
        reservation.setReservationStatus(ReservationStatus.valueOf(reservationDto.getReservationDtoStatus()));
        reservation.setModifiedDate(reservationDto.getModifiedDate());
        reservation.setTotalPrice(reservationDto.getTotalPrice());
        reservation.setNotes(reservationDto.getNotes());

        return reservation;
    }

    public List<Reservation> reservationDtoToEntity(List<ReservationDto> reservationsDto, ApartmentRepository apartmentRepository, ClientRepository clientRepository) {
        return reservationsDto.stream()
                .map(dto -> reservationDtoToEntity(dto, clientRepository, apartmentRepository))
                .collect(Collectors.toList());
    }

    public static Apartment apartmentDtoToEntity(ApartmentDto dto, ReservationService reservationService) {
        Apartment apartment = new Apartment();
        apartment.setId(dto.getId());
        apartment.setNumber(dto.getNumber());
        apartment.setPricePerNight(dto.getPricePerNight());
        apartment.setArea(dto.getArea());
        apartment.setCapacity(dto.getCapacity());
        if (dto.getReservationId() != null) {
            apartment.setReservation(reservationService.findReservationById(dto.getReservationId()));
        }
        return apartment;
    }

    public static List<Apartment> apartmentDtoToEntity(List<ApartmentDto> apartmentsDto, ReservationService reservationService) {
        List<Apartment> apartments = new ArrayList<>();
        for (ApartmentDto dto : apartmentsDto) {
            apartments.add((apartmentDtoToEntity(dto, reservationService)));
        }
        return apartments;
    }


    public static ApartmentDto apartmentToDto(Apartment apartment) {
        ApartmentDto dto = new ApartmentDto();
        dto.setId(apartment.getId());
        dto.setNumber(apartment.getNumber());
        dto.setPricePerNight(apartment.getPricePerNight());
        dto.setArea(apartment.getArea());
        dto.setCapacity(apartment.getCapacity());
        return dto;
    }

    public static List<ApartmentDto> apartmentToDto(List<Apartment> apartments) {
        List<ApartmentDto> dtos = new ArrayList<>();
        for (Apartment apartment : apartments) {
            dtos.add(apartmentToDto(apartment));
        }
        return dtos;
    }

    public static ClientDto clientToDto(Client client) {
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
    public static List<ClientDto> clientToDto(List<Client> clients) {
        List<ClientDto> dtos = new ArrayList<>();
        for (Client client : clients) {
            dtos.add(clientToDto(client));
        }
        return dtos;
    }
    public static Client clientDtoToEntity(ClientDto dto) {
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
