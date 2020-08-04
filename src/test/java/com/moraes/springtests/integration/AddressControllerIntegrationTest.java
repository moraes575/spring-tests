package com.moraes.springtests.integration;

import com.google.gson.Gson;
import com.moraes.springtests.controller.AddressController;
import com.moraes.springtests.model.Address;
import com.moraes.springtests.service.impl.AddressServiceImpl;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
class AddressControllerIntegrationTest {

    private static final Address FIRST_ADDRESS = new Address(1L, "street", "city", "state", "zipCode");
    private static final Address SECOND_ADDRESS = new Address(2L, "street", "city", "state", "zipCode");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressServiceImpl service;

    private final Gson gson = new Gson();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() throws Exception {

        when(service.findById(1L)).thenReturn(FIRST_ADDRESS);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/addresses/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(FIRST_ADDRESS)));

    }

    @Test
    void findAll() throws Exception {

        List<Address> addresses = new ArrayList<>(Arrays.asList(FIRST_ADDRESS, SECOND_ADDRESS));

        when(service.findAll()).thenReturn(addresses);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/addresses")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(addresses)));

    }

    @Test
    void save() throws Exception {

        when(service.save(FIRST_ADDRESS)).thenReturn(FIRST_ADDRESS);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addresses")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_ADDRESS));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(FIRST_ADDRESS)));

    }

    @Test
    void update() throws Exception {

        FIRST_ADDRESS.setCity("City Without Name");

        when(service.update(FIRST_ADDRESS, 1L)).thenReturn(FIRST_ADDRESS);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/addresses/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_ADDRESS));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(FIRST_ADDRESS)));

    }

    @Test
    void deleteById() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/addresses/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_ADDRESS));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));

    }

}
