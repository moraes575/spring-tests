package com.moraes.springtests.controller;

import com.moraes.springtests.model.Rental;
import com.moraes.springtests.service.interfaces.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService service;

    public RentalController(RentalService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Rental>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Rental> save(@Valid @RequestBody Rental rental) {
        rental = service.save(rental);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(rental.getId()).toUri();
        return ResponseEntity.created(uri).body(rental);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rental> update(@Valid @RequestBody Rental rental, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(rental, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
