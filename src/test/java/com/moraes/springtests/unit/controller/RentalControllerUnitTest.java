package com.moraes.springtests.unit.controller;

import com.moraes.springtests.controller.RentalController;
import com.moraes.springtests.model.Member;
import com.moraes.springtests.model.Movie;
import com.moraes.springtests.model.Rental;
import com.moraes.springtests.service.impl.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(RentalController.class)
public class RentalControllerUnitTest {

    private static final Rental FIRST_RENTAL = new Rental(1L, new Date(), new Date(), new Member(), new Movie());
    private static final Rental SECOND_RENTAL = new Rental(2L, new Date(), new Date(), new Member(), new Movie());

    private RentalController controller;

    @MockBean
    private RentalServiceImpl service;

    @BeforeEach
    void setup() {
        controller = new RentalController(service);
    }

    @Test
    void findById() {

        Long id = FIRST_RENTAL.getId();

        when(service.findById(id)).thenReturn(FIRST_RENTAL);

        assertEquals(HttpStatus.OK, controller.findById(id).getStatusCode());
        assertEquals(FIRST_RENTAL, controller.findById(id).getBody());

    }

    @Test
    void findAll() {

        List<Rental> rentals = new ArrayList<>();
        rentals.add(FIRST_RENTAL);
        rentals.add(SECOND_RENTAL);

        when(service.findAll()).thenReturn(rentals);

        assertEquals(HttpStatus.OK, controller.findAll().getStatusCode());
        assertEquals(rentals, controller.findAll().getBody());

    }

    @Test
    void save() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(service.save(FIRST_RENTAL)).thenReturn(FIRST_RENTAL);

        assertEquals(HttpStatus.CREATED, controller.save(FIRST_RENTAL).getStatusCode());
        assertEquals(FIRST_RENTAL, controller.save(FIRST_RENTAL).getBody());

    }

    @Test
    void update() {

        Long id = FIRST_RENTAL.getId();

        when(service.update(FIRST_RENTAL, id)).thenReturn(FIRST_RENTAL);

        assertEquals(HttpStatus.OK, controller.update(FIRST_RENTAL, id).getStatusCode());
        assertEquals(FIRST_RENTAL, controller.update(FIRST_RENTAL, id).getBody());

    }

    @Test
    void deleteById() {

        Long id = FIRST_RENTAL.getId();

        assertEquals(HttpStatus.NO_CONTENT, controller.deleteById(id).getStatusCode());

    }

}
