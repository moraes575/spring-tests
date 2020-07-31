package com.moraes.springtests.service.impl;

import com.moraes.springtests.model.Rental;
import com.moraes.springtests.repository.RentalRepository;
import com.moraes.springtests.service.interfaces.RentalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService {

    private final RentalRepository repository;

    public RentalServiceImpl(RentalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Rental findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found. ID: " + id));
    }

    @Override
    public List<Rental> findAll() {
        return repository.findAll();
    }

    @Override
    public Rental save(Rental rental) {
        return repository.save(rental);
    }

    @Override
    public Rental update(Rental rental, Long id) {
        Rental oldRental = findById(id);
        if (oldRental.equals(rental)) {
            return repository.save(rental);
        }
        throw new RuntimeException("Can't update different entities");
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
