package com.booking.bookingSystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "owner")
public class Owner extends User {

    @OneToMany(mappedBy = "owner")
    private List<Apartment> ownedApartments = new ArrayList<>();
}
