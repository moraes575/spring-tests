package com.moraes.springtests.unit.controller;

import com.moraes.springtests.controller.MovieController;
import com.moraes.springtests.model.Category;
import com.moraes.springtests.model.Movie;
import com.moraes.springtests.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(MovieController.class)
public class MovieControllerUnitTest {

    private static final Movie FIRST_MOVIE = new Movie(1L, "Title", "Director", new Date(), new Category(), new HashSet<>());
    private static final Movie SECOND_MOVIE = new Movie(2L, "Title", "Director", new Date(), new Category(), new HashSet<>());

    private MovieController controller;

    @MockBean
    private MovieServiceImpl service;

    @BeforeEach
    void setup() {
        controller = new MovieController(service);
    }

    @Test
    void findById() {

        Long id = FIRST_MOVIE.getId();

        when(service.findById(id)).thenReturn(FIRST_MOVIE);

        assertEquals(HttpStatus.OK, controller.findById(id).getStatusCode());
        assertEquals(FIRST_MOVIE, controller.findById(id).getBody());

    }

    @Test
    void findAll() {

        List<Movie> movies = new ArrayList<>();
        movies.add(FIRST_MOVIE);
        movies.add(SECOND_MOVIE);

        when(service.findAll()).thenReturn(movies);

        assertEquals(HttpStatus.OK, controller.findAll().getStatusCode());
        assertEquals(movies, controller.findAll().getBody());

    }

    @Test
    void save() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(service.save(FIRST_MOVIE)).thenReturn(FIRST_MOVIE);

        assertEquals(HttpStatus.CREATED, controller.save(FIRST_MOVIE).getStatusCode());
        assertEquals(FIRST_MOVIE, controller.save(FIRST_MOVIE).getBody());

    }

    @Test
    void update() {

        Long id = FIRST_MOVIE.getId();

        when(service.update(FIRST_MOVIE, id)).thenReturn(FIRST_MOVIE);

        assertEquals(HttpStatus.OK, controller.update(FIRST_MOVIE, id).getStatusCode());
        assertEquals(FIRST_MOVIE, controller.update(FIRST_MOVIE, id).getBody());

    }

    @Test
    void deleteById() {

        Long id = FIRST_MOVIE.getId();

        assertEquals(HttpStatus.NO_CONTENT, controller.deleteById(id).getStatusCode());

    }

}
