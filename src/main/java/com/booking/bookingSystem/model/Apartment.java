package com.booking.bookingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apartment")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String number;
    @NotNull
    private BigDecimal pricePerNight;
    @NotNull
    private String area;
    @NotNull
    private int capacity;
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
    @OneToOne(mappedBy = "apartment", cascade = CascadeType.ALL)
    private DigitalKey digitalKey;
}
