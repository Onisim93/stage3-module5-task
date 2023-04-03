package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {TagMapper.class, CommentMapper.class})
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    default NewsModel toModel(NewsDto newsDto) {
        NewsModel newsModel = new NewsModel();
        newsModel.setId(newsDto.getId());
        newsModel.setAuthor(new AuthorModel(newsDto.getAuthorId()));
        newsModel.setContent(newsDto.getContent());
        newsModel.setTitle(newsDto.getTitle());
        newsModel.setTags(newsDto.getTagIds().stream().map(TagModel::new).collect(Collectors.toList()));

        return newsModel;
    }

    default NewsDto toDto(NewsModel model) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(model.getId());
        newsDto.setAuthorId(model.getAuthor().getId());
        newsDto.setContent(model.getContent());
        newsDto.setTitle(newsDto.getTitle());
        newsDto.setCreateDate(model.getCreateDate());
        newsDto.setLastUpdateDate(model.getLastUpdateDate());
        newsDto.setTagList(TagMapper.INSTANCE.toListDto(model.getTags()));
        newsDto.setCommentList(CommentMapper.INSTANCE.toListDto(model.getComments()));

        return newsDto;
    }

    List<NewsDto> toListDto(List<NewsModel> newsModelList);
}
