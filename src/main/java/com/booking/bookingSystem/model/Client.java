package com.booking.bookingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "client")
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class Client extends User{

    private String preferredPaymentMethod;
    @OneToMany
    @JoinColumn(name = "reservation_id")
    private List<Reservation> pastReservations;

}
