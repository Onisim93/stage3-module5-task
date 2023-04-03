package com.mjc.school.controller;

import com.mjc.school.service.dto.BaseDto;
import org.springframework.http.ResponseEntity;

public interface BaseController<E extends BaseDto<K>, K> {

    ResponseEntity<E> readById(K id);

    ResponseEntity<E> create(E createRequest);

    ResponseEntity<E> update(K id, E updateRequest);

    ResponseEntity<E> patch(K id, E patchedRequest);

    void deleteById(K id);

}
