package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.lang.Nullable;


public interface NewsRestController extends BaseController<NewsDto, Long>{
    CollectionModel<NewsDto> getAllByCriteria(int limit, int offset, String sortBy, @Nullable String tagIds, @Nullable String tagNames, @Nullable String content, @Nullable String title, @Nullable String authorName);
    CollectionModel<NewsDto> getAllByAuthorId(Long authorId, int limit, int offset, String sortBy);
}
