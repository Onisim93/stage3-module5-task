package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.List;


public interface NewsRestController extends BaseController<NewsDto, Long>{
    ResponseEntity<List<NewsDto>> getAllByCriteria(int limit, int offset, String sortBy, @Nullable String tagIds, @Nullable String tagNames, @Nullable String content, @Nullable String title, @Nullable String authorName);
}
