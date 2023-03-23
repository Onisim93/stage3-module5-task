package com.mjc.school.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagDto implements BaseDto<Long>{
    private Long id;

    private String name;

    private List<NewsDto> news;

    public TagDto(String name) {
        this.name = name;
    }

}
