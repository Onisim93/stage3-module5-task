package com.mjc.school.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TagDto extends RepresentationModel<TagDto> implements BaseDto<Long>{
    private Long id;

    private String name;

    private List<NewsDto> news;

    public TagDto(String name) {
        this.name = name;
    }

}
