package com.moraes.springtests.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moraes.springtests.controller.MovieController;
import com.moraes.springtests.model.Category;
import com.moraes.springtests.model.Movie;
import com.moraes.springtests.service.impl.MovieServiceImpl;
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

import java.util.*;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerIntegrationTest {

    private static final Movie FIRST_MOVIE = new Movie(1L, "Title", "Director", new Date(), new Category(), new HashSet<>());
    private static final Movie SECOND_MOVIE = new Movie(2L, "Title", "Director", new Date(), new Category(), new HashSet<>());

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieServiceImpl service;

    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() throws Exception {

        when(service.findById(1L)).thenReturn(FIRST_MOVIE);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movies/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(FIRST_MOVIE.getTitle())));

    }

    @Test
    void findAll() throws Exception {

        List<Movie> movies = new ArrayList<>(Arrays.asList(FIRST_MOVIE, SECOND_MOVIE));

        when(service.findAll()).thenReturn(movies);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/movies")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", greaterThan(1)))
                .andExpect(jsonPath("$.[0].title", is(FIRST_MOVIE.getTitle())))
                .andExpect(jsonPath("$.[1].title", is(SECOND_MOVIE.getTitle())));

    }

    @Test
    void save() throws Exception {

        when(service.save(FIRST_MOVIE)).thenReturn(FIRST_MOVIE);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/movies")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_MOVIE));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(FIRST_MOVIE.getTitle())));

    }

    @Test
    void update() throws Exception {

        FIRST_MOVIE.setTitle("Other title");

        when(service.update(FIRST_MOVIE, 1L)).thenReturn(FIRST_MOVIE);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/movies/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_MOVIE));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(FIRST_MOVIE.getTitle())));

    }

    @Test
    void deleteById() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/movies/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_MOVIE));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));

    }

}
