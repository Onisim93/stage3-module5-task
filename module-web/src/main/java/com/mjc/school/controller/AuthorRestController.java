package com.mjc.school.controller;

import com.mjc.school.service.dto.AuthorDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.lang.Nullable;

public interface AuthorRestController extends BaseController<AuthorDto, Long> {
    CollectionModel<AuthorDto> getAllByCriteria(int limit, int offset, String sortBy, @Nullable String name, @Nullable Long newsId);

    AuthorDto getByNewsId(Long newsId);

    CollectionModel<AuthorDto> getAllWithNewsCount(int limit, int offset);
}
