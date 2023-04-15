package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = NewsMapper.class)
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);


    @Mapping(target = "news", ignore = true)
    TagDto toDto (TagModel tagModel);

    @Mapping(target = "news", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    TagModel toModel (TagDto tagDto);

    default List<TagDto> toListDto (List<TagModel> tagModelList) {
        Set<TagDto> resultSet = new HashSet<>();
        tagModelList.forEach(n -> resultSet.add(toDto(n)));

        return resultSet.stream().toList();
    }

}
