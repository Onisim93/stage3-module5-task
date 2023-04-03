package com.mjc.school.controller;

import com.mjc.school.service.dto.AuthorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.List;

public interface AuthorRestController extends BaseController<AuthorDto, Long> {
    ResponseEntity<List<AuthorDto>> getAllByCriteria(int limit, int offset, String sortBy, @Nullable String name, @Nullable String newsId);
}
