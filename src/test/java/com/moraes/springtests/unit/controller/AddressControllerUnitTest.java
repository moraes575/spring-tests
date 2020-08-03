package com.moraes.springtests.unit.controller;

import com.moraes.springtests.controller.AddressController;
import com.moraes.springtests.model.Address;
import com.moraes.springtests.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(AddressController.class)
public class AddressControllerUnitTest {

    private static final Address FIRST_ADDRESS = new Address(1L, "Street", "City", "State", "ZipCpde", new HashSet<>());
    private static final Address SECOND_ADDRESS = new Address(2L, "Street", "City", "State", "ZipCpde", new HashSet<>());

    private AddressController controller;

    @MockBean
    private AddressServiceImpl service;

    @BeforeEach
    void setup() {
        controller = new AddressController(service);
    }

    @Test
    void findById() {

        Long id = FIRST_ADDRESS.getId();

        when(service.findById(id)).thenReturn(FIRST_ADDRESS);

        assertEquals(HttpStatus.OK, controller.findById(id).getStatusCode());
        assertEquals(FIRST_ADDRESS, controller.findById(id).getBody());

    }

    @Test
    void findAll() {

        List<Address> addresses = new ArrayList<>();
        addresses.add(FIRST_ADDRESS);
        addresses.add(SECOND_ADDRESS);

        when(service.findAll()).thenReturn(addresses);

        assertEquals(HttpStatus.OK, controller.findAll().getStatusCode());
        assertEquals(addresses, controller.findAll().getBody());

    }

    @Test
    void save() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(service.save(FIRST_ADDRESS)).thenReturn(FIRST_ADDRESS);

        assertEquals(HttpStatus.CREATED, controller.save(FIRST_ADDRESS).getStatusCode());
        assertEquals(FIRST_ADDRESS, controller.save(FIRST_ADDRESS).getBody());

    }

    @Test
    void update() {

        Long id = FIRST_ADDRESS.getId();

        when(service.update(FIRST_ADDRESS, id)).thenReturn(FIRST_ADDRESS);

        assertEquals(HttpStatus.OK, controller.update(FIRST_ADDRESS, id).getStatusCode());
        assertEquals(FIRST_ADDRESS, controller.update(FIRST_ADDRESS, id).getBody());

    }

    @Test
    void deleteById() {

        Long id = FIRST_ADDRESS.getId();

        assertEquals(HttpStatus.NO_CONTENT, controller.deleteById(id).getStatusCode());

    }

}
