package com.moraes.springtests.service.interfaces;

import java.util.List;

public interface GenericService<T> {

    T findById(Long id);

    List<T> findAll();

    T save(T entity);

    T update(T entity, Long id);

    void deleteById(Long id);

}
