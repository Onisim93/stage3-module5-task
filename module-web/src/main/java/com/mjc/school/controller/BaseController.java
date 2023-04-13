package com.mjc.school.controller;

import com.mjc.school.service.dto.BaseDto;
import org.springframework.http.ResponseEntity;

public interface BaseController<E extends BaseDto<K>, K> {

    E readById(K id);

    E create(E createRequest);

    E update(K id, E updateRequest);

    ResponseEntity<Void> deleteById(K id);

}
