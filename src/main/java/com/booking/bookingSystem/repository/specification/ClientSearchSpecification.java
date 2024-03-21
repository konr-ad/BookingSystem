package com.booking.bookingSystem.repository.specification;

import com.booking.bookingSystem.model.Client;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ClientSearchSpecification implements SearchSpecification<Client>{

    @Override
    public Specification<Client> hasMatch(String search) {
        return (root, query, cb) -> {
            if (search == null) {
                return cb.isTrue(cb.literal(true));
            }
            return cb.or(
                    cb.like(cb.lower(root.get("lastName")), "%" + search.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("phoneNumber")), "%" + search.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("email")), "%" + search.toLowerCase() + "%")
            );
        };
    }
}