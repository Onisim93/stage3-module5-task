package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = NewsMapper.class)
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);


    @Mapping(target = "news", ignore = true)
    TagDto toDto (TagModel tagModel);

    @Mapping(target = "news", ignore = true)
    TagModel toModel (TagDto tagDto);

    List<TagDto> toListDto (List<TagModel> tagModelList);

    List<TagModel> toListModel(List<TagDto> tagDtoList);
}
