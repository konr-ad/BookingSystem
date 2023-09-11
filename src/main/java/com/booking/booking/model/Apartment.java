package com.booking.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apartment")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private BigDecimal pricePerNight;
    private int numberOfRooms;
    private int capacity;
//    private List<String> amenities;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
