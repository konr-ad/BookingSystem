package com.booking.bookingSystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "digital_key")
public class DigitalKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "encoded_Text")
    private String encodedText;

    @NotNull
    @Column(name = "valid_from")
    private LocalDateTime validFrom;

    @NotNull
    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @NotNull
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
