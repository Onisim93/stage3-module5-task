package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NewsDto extends RepresentationModel<NewsDto> implements BaseDto<Long>{
    private Long id;

    private String content;
    private String title;

    private LocalDateTime created;
    private LocalDateTime modified;

    private Long authorId;
    private List<String> tagNames;

    private List<TagDto> tagList;

    private List<CommentDto> commentList;

    public NewsDto(String content, String title, Long authorId) {
        this.content = content;
        this.title = title;
        this.authorId = authorId;
    }

    public NewsDto(String content, String title, Long authorId, List<String> tagNames) {
        this(content, title, authorId);
        this.tagNames = tagNames;
    }
}
