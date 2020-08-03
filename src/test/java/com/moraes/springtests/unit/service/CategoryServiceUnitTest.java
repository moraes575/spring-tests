package com.moraes.springtests.unit.service;

import com.moraes.springtests.model.Category;
import com.moraes.springtests.repository.CategoryRepository;
import com.moraes.springtests.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceUnitTest {

    private static final Category FIRST_CATEGORY = new Category(1L, "Category", new HashSet<>());
    private static final Category SECOND_CATEGORY = new Category(2L, "Category", new HashSet<>());

    @Autowired
    private CategoryServiceImpl service;

    @MockBean
    private CategoryRepository repository;

    @Test
    void findById() {

        Long id = FIRST_CATEGORY.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_CATEGORY));

        assertEquals(FIRST_CATEGORY, service.findById(id));

    }

    @Test
    void findAll() {

        List<Category> categories = new ArrayList<>();
        categories.add(FIRST_CATEGORY);
        categories.add(SECOND_CATEGORY);

        when(repository.findAll()).thenReturn(categories);

        assertEquals(categories, service.findAll());

    }

    @Test
    void save() {

        when(repository.save(FIRST_CATEGORY)).thenReturn(FIRST_CATEGORY);

        assertEquals(FIRST_CATEGORY, service.save(FIRST_CATEGORY));

    }

    @Test
    void update() {

        Long id = FIRST_CATEGORY.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_CATEGORY));
        when(repository.save(FIRST_CATEGORY)).thenReturn(FIRST_CATEGORY);

        assertEquals(FIRST_CATEGORY, service.update(FIRST_CATEGORY, id));

    }

    @Test
    void deleteById() {

        Long id = FIRST_CATEGORY.getId();

        when(repository.findById(id)).thenReturn(Optional.of(FIRST_CATEGORY));
        service.deleteById(id);

        then(repository).should().deleteById(id);

    }

}