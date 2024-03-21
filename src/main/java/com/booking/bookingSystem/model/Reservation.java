package com.booking.bookingSystem.model;

import com.booking.bookingSystem.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "reservation")
    private List<Apartment> apartments;
    @NotNull
    @Column(name = "start_date")
    private LocalDate startDate;
    @NotNull
    @Column(name = "end_date")
    private LocalDate endDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;
    @NotNull
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    @NotNull
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "notes")
    private String notes;
}
