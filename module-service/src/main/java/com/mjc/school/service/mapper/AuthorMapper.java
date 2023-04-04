package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = NewsMapper.class)
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    default AuthorDto toDto(AuthorModel authorModel) {
        AuthorDto dto = new AuthorDto();
        dto.setId(authorModel.getId());
        dto.setName(authorModel.getName());
        dto.setCreateDate(authorModel.getCreateDate());
        dto.setLastUpdateDate(authorModel.getLastUpdateDate());

        return dto;
    }

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "news", ignore = true)
    AuthorModel toModel(AuthorDto authorDto);

    List<AuthorDto> toListDto(List<AuthorModel> authorModelList);
}
