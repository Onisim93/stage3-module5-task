package com.mjc.school.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsDto implements BaseDto<Long>{
    private Long id;

    @NotBlank
    @Size(min = 5, max = 255)
    private String content;
    @NotBlank
    @Size(min = 5, max = 30)
    private String title;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    @NotNull
    @Range(min = 1)
    private Long authorId;
    private List<TagDto> tagList;
    private List<Long> tagIds;
    private List<CommentDto> commentList;

    public NewsDto(String content, String title, Long authorId) {
        this.content = content;
        this.title = title;
        this.authorId = authorId;
    }

    public NewsDto(String content, String title, Long authorId, List<Long> tagIds) {
        this(content, title, authorId);
        this.tagIds = tagIds;
    }
}
