package com.booking.bookingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApartmentDto {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String description;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotNull(message = "Price per night cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
    private BigDecimal pricePerNight;

    @NotNull(message = "Number of rooms cannot be null")
    @Min(value = 1, message = "Number of rooms must be at least 1")
    private int numberOfRooms;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @NotBlank(message = "Location cannot be blank")
    private String location;
}
