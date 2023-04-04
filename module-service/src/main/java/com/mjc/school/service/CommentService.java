package com.mjc.school.service;

import com.mjc.school.service.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CommentService extends BaseService<CommentDto, Long>{
    List<CommentDto> getAllByNewsId(Long newsId);

    Page<CommentDto> getAllByCriteria(int limit, int offset, String sortBy, @Nullable String newsId, @Nullable String content);

}
