package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthorDto extends RepresentationModel<AuthorDto> implements BaseDto<Long>{
    private Long id;
    private String name;
    private LocalDateTime created;
    private LocalDateTime modified;
    private List<NewsDto> news;
    private Integer newsCount;



}
