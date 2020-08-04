package com.moraes.springtests.integration;

import com.google.gson.Gson;
import com.moraes.springtests.controller.CategoryController;
import com.moraes.springtests.model.Category;
import com.moraes.springtests.service.impl.CategoryServiceImpl;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerIntegrationTest {

    private static final Category FIRST_CATEGORY = new Category(1L, "Category");
    private static final Category SECOND_CATEGORY = new Category(2L, "Category 02");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryServiceImpl service;

    private final Gson gson = new Gson();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findById() throws Exception {

        when(service.findById(1L)).thenReturn(FIRST_CATEGORY);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(FIRST_CATEGORY)));

    }

    @Test
    void findAll() throws Exception {

        List<Category> categories = new ArrayList<>(Arrays.asList(FIRST_CATEGORY, SECOND_CATEGORY));

        when(service.findAll()).thenReturn(categories);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(categories)));

    }

    @Test
    void save() throws Exception {

        when(service.save(FIRST_CATEGORY)).thenReturn(FIRST_CATEGORY);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/categories")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_CATEGORY));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(FIRST_CATEGORY)));

    }

    @Test
    void update() throws Exception {

        FIRST_CATEGORY.setName("Other category");

        when(service.update(FIRST_CATEGORY, 1L)).thenReturn(FIRST_CATEGORY);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/categories/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_CATEGORY));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(gson.toJson(FIRST_CATEGORY)));

    }

    @Test
    void deleteById() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/categories/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(gson.toJson(FIRST_CATEGORY));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));

    }

}
