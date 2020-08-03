package com.moraes.springtests.unit.service;

import com.moraes.springtests.model.Member;
import com.moraes.springtests.model.Movie;
import com.moraes.springtests.model.Rental;
import com.moraes.springtests.repository.RentalRepository;
import com.moraes.springtests.service.impl.RentalServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@SpringBootTest
class RentalServiceUnitTest {

    private static final Rental FIRST_RENTAL = new Rental(1L, new Date(), new Date(), new Member(), new Movie());
    private static final Rental SECOND_RENTAL = new Rental(2L, new Date(), new Date(), new Member(), new Movie());

    @Autowired
    private RentalServiceImpl service;

    @MockBean
    private RentalRepository repository;

    @Test
    void findById() {

        Long id = FIRST_RENTAL.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_RENTAL));

        assertEquals(FIRST_RENTAL, service.findById(id));

    }

    @Test
    void findAll() {

        List<Rental> rentals = new ArrayList<>();
        rentals.add(FIRST_RENTAL);
        rentals.add(SECOND_RENTAL);

        when(repository.findAll()).thenReturn(rentals);

        assertEquals(rentals, service.findAll());

    }

    @Test
    void save() {

        when(repository.save(FIRST_RENTAL)).thenReturn(FIRST_RENTAL);

        assertEquals(FIRST_RENTAL, service.save(FIRST_RENTAL));

    }

    @Test
    void update() {

        Long id = FIRST_RENTAL.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_RENTAL));
        when(repository.save(FIRST_RENTAL)).thenReturn(FIRST_RENTAL);

        assertEquals(FIRST_RENTAL, service.update(FIRST_RENTAL, id));

    }

    @Test
    void deleteById() {

        Long id = FIRST_RENTAL.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_RENTAL));
        service.deleteById(id);

        then(repository).should().deleteById(id);

    }

}