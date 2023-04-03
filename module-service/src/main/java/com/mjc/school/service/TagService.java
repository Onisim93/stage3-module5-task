package com.mjc.school.service;

import com.mjc.school.service.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;

public interface TagService extends BaseService<TagDto, Long>{
    List<TagDto> getAllByNewsId(Long newsId);

    Page<TagDto> getAllByCriteria(int limit, int offset, String sortBy, @Nullable String newsId,@Nullable String name);
}
