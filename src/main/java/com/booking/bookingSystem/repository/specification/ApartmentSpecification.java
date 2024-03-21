package com.booking.bookingSystem.repository.specification;

import com.booking.bookingSystem.model.Apartment;
import org.springframework.data.jpa.domain.Specification;
import java.math.BigDecimal;

public class ApartmentSpecification {

    public static Specification<Apartment> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null || maxPrice == null) {
                return cb.isTrue(cb.literal(true)); // jeśli jedna z wartości jest null, nie filtrujemy po tej wartości
            }
            return cb.between(root.get("pricePerNight"), minPrice, maxPrice);
        };
    }

    public static Specification<Apartment> capacityBetween(Integer minCapacity, Integer maxCapacity) {
        return (root, query, cb) -> {
            if (minCapacity == null || maxCapacity == null) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.between(root.get("capacity"), minCapacity, maxCapacity);
        };
    }

    public static Specification<Apartment> areaLike(String area) {
        return (root, query, cb) -> {
            if (area == null) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.like(cb.lower(root.get("area")), "%" + area.toLowerCase() + "%");
        };
    }
}
