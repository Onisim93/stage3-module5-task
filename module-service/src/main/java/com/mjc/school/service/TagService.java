package com.mjc.school.service;

import com.mjc.school.service.dto.TagDto;

import java.util.List;

public interface TagService extends BaseService<TagDto, Long>{
    List<TagDto> getAllByNewsId(Long newsId);
}
