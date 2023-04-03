package com.mjc.school.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class AuthorDto implements BaseDto<Long>{
    private Long id;


    @NotBlank
    @Size(min = 3, max = 15)
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<NewsDto> news;

    public AuthorDto(String name) {
        this.name = name;
    }


}
