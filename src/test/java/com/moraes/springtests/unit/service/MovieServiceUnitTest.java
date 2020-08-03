package com.moraes.springtests.unit.service;

import com.moraes.springtests.model.Category;
import com.moraes.springtests.model.Movie;
import com.moraes.springtests.repository.MovieRepository;
import com.moraes.springtests.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@SpringBootTest
class MovieServiceUnitTest {

    private static final Movie FIRST_MOVIE = new Movie(1L, "Title", "Director", new Date(), new Category(), new HashSet<>());
    private static final Movie SECOND_MOVIE = new Movie(2L, "Title", "Director", new Date(), new Category(), new HashSet<>());

    @Autowired
    private MovieServiceImpl service;

    @MockBean
    private MovieRepository repository;

    @Test
    void findById() {

        Long id = FIRST_MOVIE.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_MOVIE));

        assertEquals(FIRST_MOVIE, service.findById(id));

    }

    @Test
    void findAll() {

        List<Movie> movies = new ArrayList<>();
        movies.add(FIRST_MOVIE);
        movies.add(SECOND_MOVIE);

        when(repository.findAll()).thenReturn(movies);

        assertEquals(movies, service.findAll());

    }

    @Test
    void save() {

        when(repository.save(FIRST_MOVIE)).thenReturn(FIRST_MOVIE);

        assertEquals(FIRST_MOVIE, service.save(FIRST_MOVIE));

    }

    @Test
    void update() {

        Long id = FIRST_MOVIE.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_MOVIE));
        when(repository.save(FIRST_MOVIE)).thenReturn(FIRST_MOVIE);

        assertEquals(FIRST_MOVIE, service.update(FIRST_MOVIE, id));

    }

    @Test
    void deleteById() {

        Long id = FIRST_MOVIE.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_MOVIE));
        service.deleteById(id);

        then(repository).should().deleteById(id);

    }

}