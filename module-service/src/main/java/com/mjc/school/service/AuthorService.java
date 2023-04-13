package com.mjc.school.service;

import com.mjc.school.service.dto.AuthorDto;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

public interface AuthorService extends BaseService<AuthorDto, Long>{

    AuthorDto getByNewsId(Long newsId);

    Page<AuthorDto> getAllByCriteria(int limit, int offset, String sortBy, @Nullable Long newsId,@Nullable String name, boolean withNews);



}
