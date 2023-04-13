package com.mjc.school.controller;

import com.mjc.school.service.dto.TagDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.lang.Nullable;

public interface TagRestController extends BaseController<TagDto, Long>{
    CollectionModel<TagDto> getAllByCriteria(int limit, int offset, @Nullable String sortBy, @Nullable String name, @Nullable Long newsId);

    CollectionModel<TagDto> getAllByNewsId(Long newsId, int limit, int offset, @Nullable String sortBy);
}
