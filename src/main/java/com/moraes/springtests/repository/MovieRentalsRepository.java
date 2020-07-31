package com.moraes.springtests.repository;

import com.moraes.springtests.model.MovieRentals;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRentalsRepository extends JpaRepository<MovieRentals, Long> {
}
