package com.moraes.springtests.unit.controller;

import com.moraes.springtests.controller.CategoryController;
import com.moraes.springtests.model.Category;
import com.moraes.springtests.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(CategoryController.class)
public class CategoryControllerUnitTest {

    private static final Category FIRST_CATEGORY = new Category(1L, "Category", new HashSet<>());
    private static final Category SECOND_CATEGORY = new Category(2L, "Category", new HashSet<>());

    private CategoryController controller;

    @MockBean
    private CategoryServiceImpl service;

    @BeforeEach
    void setup() {
        controller = new CategoryController(service);
    }

    @Test
    void findById() {

        Long id = FIRST_CATEGORY.getId();

        when(service.findById(id)).thenReturn(FIRST_CATEGORY);

        assertEquals(HttpStatus.OK, controller.findById(id).getStatusCode());
        assertEquals(FIRST_CATEGORY, controller.findById(id).getBody());

    }

    @Test
    void findAll() {

        List<Category> categories = new ArrayList<>();
        categories.add(FIRST_CATEGORY);
        categories.add(SECOND_CATEGORY);

        when(service.findAll()).thenReturn(categories);

        assertEquals(HttpStatus.OK, controller.findAll().getStatusCode());
        assertEquals(categories, controller.findAll().getBody());

    }

    @Test
    void save() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(service.save(FIRST_CATEGORY)).thenReturn(FIRST_CATEGORY);

        assertEquals(HttpStatus.CREATED, controller.save(FIRST_CATEGORY).getStatusCode());
        assertEquals(FIRST_CATEGORY, controller.save(FIRST_CATEGORY).getBody());

    }

    @Test
    void update() {

        Long id = FIRST_CATEGORY.getId();

        when(service.update(FIRST_CATEGORY, id)).thenReturn(FIRST_CATEGORY);

        assertEquals(HttpStatus.OK, controller.update(FIRST_CATEGORY, id).getStatusCode());
        assertEquals(FIRST_CATEGORY, controller.update(FIRST_CATEGORY, id).getBody());

    }

    @Test
    void deleteById() {

        Long id = FIRST_CATEGORY.getId();

        assertEquals(HttpStatus.NO_CONTENT, controller.deleteById(id).getStatusCode());

    }

}
