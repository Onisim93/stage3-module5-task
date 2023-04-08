package com.mjc.school.controller;

import com.mjc.school.service.dto.CommentDto;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CommentRestController extends BaseController<CommentDto, Long>{
    List<CommentDto> getAllByCriteria(int limit, int offset, String sortBy, @Nullable String content, @Nullable String newsId);
}
