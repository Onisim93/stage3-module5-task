package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorDto;

public interface AuthorService extends BaseService<AuthorDto, Long>{

    AuthorDto getByNewsId(Long newsId);

}
