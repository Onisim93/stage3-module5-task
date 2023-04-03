package com.mjc.school.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TagDto implements BaseDto<Long>{
    private Long id;
    @NotBlank
    @Size(min = 3, max = 15)
    private String name;

    private List<NewsDto> news;

    public TagDto(String name) {
        this.name = name;
    }

}
