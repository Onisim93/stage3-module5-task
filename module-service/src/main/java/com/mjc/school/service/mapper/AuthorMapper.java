package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    default List<AuthorDto> toListDto(List<AuthorModel> authorModelList) {
        Set<AuthorDto> resultSet = new HashSet<>();
        authorModelList.forEach(a -> resultSet.add(toDto(a)));

        return resultSet.stream().toList();
    }

    @IterableMapping(qualifiedByName = {"dtoWithNewsCount"})
    default List<AuthorDto> toListDtoWithNewsCount(List<AuthorModel> authorModelList) {
        Set<AuthorDto> resultSet = new HashSet<>();
        authorModelList.forEach(a -> resultSet.add(toDtoWithNewsCount(a)));

        return resultSet.stream().toList();
    }
}
