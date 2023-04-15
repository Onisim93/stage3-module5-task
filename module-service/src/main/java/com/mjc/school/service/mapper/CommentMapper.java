package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    default CommentDto toDto(CommentModel model) {
        CommentDto dto = new CommentDto();
        dto.setId(model.getId());
        dto.setContent(model.getContent());
        dto.setCreated(model.getCreated());
        dto.setModified(model.getModified());
        dto.setNewsId(model.getNews().getId());

        return dto;
    }

    default CommentModel toModel(CommentDto commentDto) {
        CommentModel model = new CommentModel();
        model.setId(commentDto.getId());
        model.setContent(commentDto.getContent());
        model.setNews(new NewsModel(commentDto.getNewsId()));

        return model;
    }

    default List<CommentDto> toListDto(List<CommentModel> commentModelList) {
        Set<CommentDto> resultSet = new HashSet<>();
        commentModelList.forEach(n -> resultSet.add(toDto(n)));

        return resultSet.stream().toList();
    }

}
