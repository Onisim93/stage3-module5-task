package com.mjc.school.service;

import com.mjc.school.service.dto.BaseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseService<E extends BaseDto<K>, K> {
    List<E> readAll();

    E readById(K id);

    E create(E createRequest);

    E update(E updateRequest);

    boolean deleteById(K id);

    Page<E> getAllByCriteria(int page, int limit, String sortBy, List<String> filterParams);
}
