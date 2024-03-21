package com.booking.bookingSystem.repository.specification;

import com.booking.bookingSystem.enums.ReservationStatus;
import com.booking.bookingSystem.model.Reservation;
import com.booking.bookingSystem.model.Client;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;


public class ReservationSpecification {

    public static Specification<Reservation> hasClientLastName(String lastName) {
        return (root, query, cb) -> {
            if (lastName == null || lastName.isEmpty()) {
                return cb.isTrue(cb.literal(true));
            }
            Join<Reservation, Client> clientJoin = root.join("client", JoinType.INNER);
            return cb.like(cb.lower(clientJoin.get("lastName")), "%" + lastName.toLowerCase() + "%");
        };
    }

    public static Specification<Reservation> hasClientPhoneNumber(String phoneNumber) {
        return (root, query, cb) -> {
            if (phoneNumber == null || phoneNumber.isEmpty()) {
                return cb.isTrue(cb.literal(true));
            }
            Join<Reservation, Client> clientJoin = root.join("client", JoinType.INNER);
            return cb.like(cb.lower(clientJoin.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%");
        };
    }

    public static Specification<Reservation> hasReservationStatus(ReservationStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.equal(root.get("reservationStatus"), status);
        };
    }
}
