package com.booking.bookingSystem.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SearchSpecification<T> {

    Specification<T> hasMatch(String search);
}
