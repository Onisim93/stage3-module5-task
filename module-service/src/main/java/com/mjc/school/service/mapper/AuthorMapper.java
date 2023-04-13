package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = NewsMapper.class)
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Named(value = "baseDto")
    @Mapping(target = "news", ignore = true)
    @Mapping(target = "newsCount", ignore = true)
    AuthorDto toDto(AuthorModel authorModel);

    @Named(value = "dtoWithNewsCount")
    default AuthorDto toDtoWithNewsCount(AuthorModel authorModel) {
        AuthorDto dto = new AuthorDto();
        dto.setId(authorModel.getId());
        dto.setName(authorModel.getName());
        dto.setCreated(authorModel.getCreated());
        dto.setModified(authorModel.getModified());
        dto.setNewsCount(authorModel.getNews().size());

        return dto;
    }



    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "news", ignore = true)
    AuthorModel toModel(AuthorDto authorDto);

    @IterableMapping(qualifiedByName = {"baseDto"})
    List<AuthorDto> toListDto(List<AuthorModel> authorModelList);

    @IterableMapping(qualifiedByName = {"dtoWithNewsCount"})
    List<AuthorDto> toListDtoWithNewsCount(List<AuthorModel> authorModelList);
}
