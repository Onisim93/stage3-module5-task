package com.mjc.school.service;

import com.mjc.school.service.dto.NewsDto;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

public interface NewsService extends BaseService<NewsDto, Long> {
    Page<NewsDto> getAllByCriteria(int limit, int offset, String sortBy, @Nullable String tagIds,@Nullable String tagNames,@Nullable String content,@Nullable String title,@Nullable String authorName);

    Page<NewsDto> getAllByAuthorId(Long authorId, int limit, int offset, String sortBy);
}
