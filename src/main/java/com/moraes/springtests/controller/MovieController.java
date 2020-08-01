package com.moraes.springtests.controller;

import com.moraes.springtests.model.Movie;
import com.moraes.springtests.service.interfaces.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Movie> save(@Valid @RequestBody Movie movie) {
        movie = service.save(movie);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(movie.getId()).toUri();
        return ResponseEntity.created(uri).body(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@Valid @RequestBody Movie movie, @PathVariable Long id) {
        return ResponseEntity.ok(service.update(movie, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
