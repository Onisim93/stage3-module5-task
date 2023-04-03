package com.mjc.school.service.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class CommentDto implements BaseDto<Long>{
    private Long id;

    @NotBlank
    @Size(min = 5, max = 255)
    private String content;
    @NotNull
    @Range(min = 1)
    private Long newsId;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
