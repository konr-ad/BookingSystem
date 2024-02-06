package com.booking.bookingSystem.dto;

import com.booking.bookingSystem.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDto {

    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, message = "First name must be at least 2 characters long")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, message = "Last name must be at least 2 characters long")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    private String preferredPaymentMethod;

    private Long reservationDtoId;
}