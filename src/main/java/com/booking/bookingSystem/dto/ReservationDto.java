package com.booking.bookingSystem.dto;

import com.booking.bookingSystem.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private Long id;

    @NotNull(message = "Client cannot be null")
    private Long clientDtoId;

    @NotNull(message = "Apartment can not be empty")
    private List<Long> apartmentsDtoIds;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be in the present or future")
    private LocalDate endDate;

    @NotNull(message = "Reservation status is required")
    private String reservationDtoStatus;

    private LocalDateTime modifiedDate;

    @NotNull(message = "Total price is required")
    private BigDecimal totalPrice;

    private String notes;

}
