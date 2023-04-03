package com.mjc.school.controller;

import com.mjc.school.service.dto.TagDto;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.List;

public interface TagRestController extends BaseController<TagDto, Long>{
    ResponseEntity<List<TagDto>> getAllByCriteria(int limit, int offset, @Nullable String sortBy, @Nullable String name, @Nullable String newsId);
}
