package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class TagDto extends RepresentationModel<TagDto> implements BaseDto<Long>{
    private Long id;

    private String name;

    private List<NewsDto> news;

    private LocalDateTime created;
    private LocalDateTime modified;

    public TagDto(String name) {
        this.name = name;
    }

}
