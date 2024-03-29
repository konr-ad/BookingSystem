package com.booking.bookingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDto {

    private Long id;
    private String name;
    private String description;
    private String address;
    private BigDecimal pricePerNight;
    private int numberOfRooms;
    private int capacity;
    private OwnerDto owner;
    private String location;
}
