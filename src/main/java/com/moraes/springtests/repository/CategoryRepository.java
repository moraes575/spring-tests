package com.moraes.springtests.repository;

import com.moraes.springtests.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
