package com.mjc.school.service;

import com.mjc.school.service.dto.CommentDto;

import java.util.List;

public interface CommentService extends BaseService<CommentDto, Long>{
    List<CommentDto> getAllByNewsId(Long newsId);
}
