package com.mjc.school.controller;

import com.mjc.school.service.dto.BaseDto;

public interface BaseController<E extends BaseDto<K>, K> {

    E readById(K id);

    E create(E createRequest);

    E update(K id, E updateRequest);

    E patch(K id, E patchedRequest);

    void deleteById(K id);

}
