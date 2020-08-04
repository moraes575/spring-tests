package com.moraes.springtests.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moraes.springtests.controller.RentalController;
import com.moraes.springtests.model.Member;
import com.moraes.springtests.model.Movie;
import com.moraes.springtests.model.Rental;
import com.moraes.springtests.service.impl.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentalController.class)
class RentalControllerIntegrationTest {

    private static final Rental FIRST_RENTAL = new Rental(1L, new Date(), new Date(), new Member(), new Movie());
    private static final Rental SECOND_RENTAL = new Rental(2L, new Date(), new Date(), new Member(), new Movie());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalServiceImpl service;

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() throws Exception {

        when(service.findById(1L)).thenReturn(FIRST_RENTAL);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rentals/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.member.name", is(FIRST_RENTAL.getMember().getName())));

    }

    @Test
    void findAll() throws Exception {

        List<Rental> rentals = new ArrayList<>(Arrays.asList(FIRST_RENTAL, SECOND_RENTAL));

        when(service.findAll()).thenReturn(rentals);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rentals")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", greaterThan(1)))
                .andExpect(jsonPath("$.[0].member.name", is(FIRST_RENTAL.getMember().getName())))
                .andExpect(jsonPath("$.[0].member.name", is(SECOND_RENTAL.getMember().getName())));

    }

    @Test
    void save() throws Exception {

        when(service.save(FIRST_RENTAL)).thenReturn(FIRST_RENTAL);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rentals")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_RENTAL));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.member.name", is(FIRST_RENTAL.getMember().getName())));

    }

    @Test
    void update() throws Exception {

        FIRST_RENTAL.getMovie().setTitle("Other title");

        when(service.update(FIRST_RENTAL, 1L)).thenReturn(FIRST_RENTAL);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rentals/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_RENTAL));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.member.name", is(FIRST_RENTAL.getMember().getName())));

    }

    @Test
    void deleteById() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/rentals/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_RENTAL));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));

    }

}
