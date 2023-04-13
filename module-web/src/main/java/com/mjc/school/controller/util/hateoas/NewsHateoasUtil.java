package com.mjc.school.controller.util.hateoas;

import com.mjc.school.controller.impl.AuthorController;
import com.mjc.school.controller.impl.CommentController;
import com.mjc.school.controller.impl.NewsController;
import com.mjc.school.controller.impl.TagController;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.dto.NewsDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class NewsHateoasUtil {

    private NewsHateoasUtil() {}
    public static void addHateoas(NewsDto newsDto, boolean withLinkToGetAll) {
        newsDto.add(linkTo(methodOn(NewsController.class).readById(newsDto.getId())).withSelfRel());
        newsDto.add(linkTo(methodOn(NewsController.class).update(newsDto.getId(), newsDto)).withRel("update"));
        newsDto.add(linkTo(methodOn(NewsController.class).deleteById(newsDto.getId())).withRel("delete"));
        newsDto.add(linkTo(methodOn(CommentController.class).getAllByNewsId(newsDto.getId(), 20,1, null, null)).withRel("get comments").expand());
        newsDto.add(linkTo(methodOn(CommentController.class).create(newsDto.getId(), new CommentDto())).withRel("create comment").expand());
        newsDto.add(linkTo(methodOn(AuthorController.class).getByNewsId(newsDto.getId())).withRel("get author"));
        newsDto.add(linkTo(methodOn(TagController.class).getAllByNewsId(newsDto.getId(), 20, 1, null)).withRel("get tags").expand());
        if (withLinkToGetAll) {
            newsDto.add(linkTo(methodOn(NewsController.class).getAllByCriteria(20, 1, null, null, null, null, null, null)).withRel("Get all news").expand());
        }
    }

    public static CollectionModel<NewsDto> getAllByAuthorIdWithHateoas(Page<NewsDto> dtos, Long authorId, int limit, int offset, String sortBy) {
        dtos.getContent().forEach(a -> addHateoas(a, false));
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(NewsController.class).getAllByAuthorId(authorId, limit, 1, sortBy)).withSelfRel().expand());
        links.add(linkTo(methodOn(NewsController.class).create(new NewsDto())).withRel("create news").expand());

        if (dtos.hasNext()) {
            links.add(linkTo(methodOn(NewsController.class).getAllByAuthorId(authorId, limit, offset+1, sortBy)).withRel("next page").expand());
        }
        if (dtos.hasPrevious()) {
            links.add(linkTo(methodOn(NewsController.class).getAllByAuthorId(authorId, limit, offset-1, sortBy)).withRel("previous page").expand());
        }

        return CollectionModel.of(dtos.getContent(), links);
    }

    public static CollectionModel<NewsDto> getAllWithHateoas(Page<NewsDto> dtos, int limit, int offset, String sortBy, String tagIds, String tagNames, String content, String title, String authorName) {
        dtos.getContent().forEach(a -> addHateoas(a, false));

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(NewsController.class).getAllByCriteria(limit, 1, null, null, null, null, null, null)).withSelfRel().expand());
        links.add(linkTo(methodOn(NewsController.class).create(new NewsDto())).withRel("create news").expand());


        if (dtos.hasNext()) {
            links.add(linkTo(methodOn(NewsController.class).getAllByCriteria(limit, offset+1, sortBy, tagIds, tagNames, content, title, authorName)).withRel("next page").expand());
        }
        if (dtos.hasPrevious()) {
            links.add(linkTo(methodOn(NewsController.class).getAllByCriteria(limit, offset-1, sortBy, tagIds, tagNames, content, title, authorName)).withRel("previous page").expand());
        }

        return CollectionModel.of(dtos.getContent(), links);
    }
}
