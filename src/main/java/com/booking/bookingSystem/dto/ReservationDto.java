package com.booking.bookingSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private Long id;
    private Long apartmentId;
    private Long clientId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdDate;
}
