package com.moraes.springtests.service.impl;

import com.moraes.springtests.model.Movie;
import com.moraes.springtests.repository.MovieRepository;
import com.moraes.springtests.service.interfaces.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;

    public MovieServiceImpl(MovieRepository repository) {
        this.repository = repository;
    }

    @Override
    public Movie findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found. ID: " + id));
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    public Movie save(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public Movie update(Movie movie, Long id) {
        Movie oldMovie = findById(id);
        if (oldMovie.equals(movie)) {
            return repository.save(movie);
        }
        throw new RuntimeException("Can't update different entities");
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
