package com.moraes.springtests.service.impl;

import com.moraes.springtests.model.Address;
import com.moraes.springtests.repository.AddressRepository;
import com.moraes.springtests.service.interfaces.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;

    public AddressServiceImpl(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found. ID: " + id));
    }

    @Override
    public List<Address> findAll() {
        return repository.findAll();
    }

    @Override
    public Address save(Address address) {
        return repository.save(address);
    }

    @Override
    public Address update(Address address, Long id) {
        Address oldAddress = findById(id);
        if (oldAddress.equals(address)) {
            return repository.save(address);
        }
        throw new RuntimeException("Can't update different entities");
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
