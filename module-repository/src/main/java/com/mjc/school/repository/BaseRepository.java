package com.mjc.school.repository;

import com.mjc.school.repository.model.BaseEntity;

import java.util.List;
import java.util.Optional;


public interface BaseRepository<E extends BaseEntity<K>, K> {

    List<E> readAll();

    Optional<E> readById(K id);

    E create(E entity);

    E update(E entity);

    boolean deleteById(K id);

    boolean existById(K id);
}
