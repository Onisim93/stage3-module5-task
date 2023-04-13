package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommentDto extends RepresentationModel<CommentDto> implements BaseDto<Long>{
    private Long id;

    private String content;

    private Long newsId;
    private LocalDateTime created;
    private LocalDateTime modified;
}
