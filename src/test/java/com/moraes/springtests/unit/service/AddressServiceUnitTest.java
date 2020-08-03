package com.moraes.springtests.unit.service;

import com.moraes.springtests.model.Address;
import com.moraes.springtests.repository.AddressRepository;
import com.moraes.springtests.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressServiceUnitTest {

    private static final Address FIRST_ADDRESS = new Address(1L, "Street", "City", "State", "ZipCpde", new HashSet<>());
    private static final Address SECOND_ADDRESS = new Address(2L, "Street", "City", "State", "ZipCpde", new HashSet<>());

    @Autowired
    private AddressServiceImpl service;

    @MockBean
    private AddressRepository repository;

    @Test
    void findById() {

        Long id = FIRST_ADDRESS.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_ADDRESS));

        assertEquals(FIRST_ADDRESS, service.findById(id));

    }

    @Test
    void findAll() {

        List<Address> addresses = new ArrayList<>();
        addresses.add(FIRST_ADDRESS);
        addresses.add(SECOND_ADDRESS);

        when(repository.findAll()).thenReturn(addresses);

        assertEquals(addresses, service.findAll());

    }

    @Test
    void save() {

        when(repository.save(FIRST_ADDRESS)).thenReturn(FIRST_ADDRESS);

        assertEquals(FIRST_ADDRESS, service.save(FIRST_ADDRESS));

    }

    @Test
    void update() {

        Long id = FIRST_ADDRESS.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_ADDRESS));
        when(repository.save(FIRST_ADDRESS)).thenReturn(FIRST_ADDRESS);

        assertEquals(FIRST_ADDRESS, service.update(FIRST_ADDRESS, id));

    }

    @Test
    void deleteById() {

        Long id = FIRST_ADDRESS.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_ADDRESS));
        service.deleteById(id);

        then(repository).should().deleteById(id);

    }

}