package com.mjc.school.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto implements BaseDto<Long>{
    private Long id;
    private String content;
    private Long newsId;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
