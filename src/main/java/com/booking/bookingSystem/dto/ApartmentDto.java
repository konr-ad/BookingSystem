package com.booking.bookingSystem.dto;

import com.booking.bookingSystem.model.Reservation;
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

    @NotBlank(message = "Number cannot be blank")
    private String number;

    @NotNull(message = "Price per night cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per night must be greater than 0")
    private BigDecimal pricePerNight;

    @DecimalMin(value = "0.0", inclusive = false, message = "Area must be greater than 0")
    @NotNull
    private String area;

    @NotNull(message = "Capacity cannot be null")
    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    private Long reservationId;
}
