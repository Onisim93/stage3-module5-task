package com.mjc.school.service;

import com.mjc.school.service.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

public interface TagService extends BaseService<TagDto, Long>{
    Page<TagDto> getAllByCriteria(int limit, int offset, String sortBy, @Nullable Long newsId,@Nullable String name);
}
