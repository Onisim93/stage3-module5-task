package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

public interface CommentRestController{


    CollectionModel<CommentDto> getAllByNewsId(Long newsId, int limit, int offset, @Nullable String sortBy, @Nullable String content);

    CommentDto create(Long newsId, CommentDto requestDto);

    CommentDto readById(Long newsId, Long id);


    CommentDto update(Long id, CommentDto updateRequest);

    ResponseEntity<Void> deleteById(Long newsId, Long id);
}
