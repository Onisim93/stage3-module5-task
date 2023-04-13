package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TagMapper.class, CommentMapper.class})
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    default NewsModel toModel(NewsDto newsDto) {
        NewsModel newsModel = new NewsModel();
        newsModel.setId(newsDto.getId());
        newsModel.setAuthor(new AuthorModel(newsDto.getAuthorId()));
        newsModel.setContent(newsDto.getContent());
        newsModel.setTitle(newsDto.getTitle());

        return newsModel;
    }

    default NewsDto toDto(NewsModel model) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(model.getId());
        newsDto.setAuthorId(model.getAuthor().getId());
        newsDto.setContent(model.getContent());
        newsDto.setTitle(model.getTitle());
        newsDto.setCreated(model.getCreated());
        newsDto.setModified(model.getModified());
        newsDto.setTagList(TagMapper.INSTANCE.toListDto(model.getTags()));
        newsDto.setCommentList(CommentMapper.INSTANCE.toListDto(model.getComments()));

        return newsDto;
    }

    List<NewsDto> toListDto(List<NewsModel> newsModelList);
}
