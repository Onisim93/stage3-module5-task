package com.mjc.school.service;

import com.mjc.school.service.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

public interface CommentService extends BaseService<CommentDto, Long>{
    Page<CommentDto> getAllByNewsId(Long newsId, int limit, int offset, String sortBy, @Nullable String content);

}
