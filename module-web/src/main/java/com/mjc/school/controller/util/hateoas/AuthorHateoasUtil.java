package com.mjc.school.controller.util.hateoas;

import com.mjc.school.controller.impl.AuthorController;
import com.mjc.school.controller.impl.NewsController;
import com.mjc.school.service.dto.AuthorDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class AuthorHateoasUtil {

    private AuthorHateoasUtil() {}
    public static void addHateoas(AuthorDto authorDto, boolean withLinkToGetAll) {
        authorDto.add(linkTo(methodOn(AuthorController.class).readById(authorDto.getId())).withSelfRel());
        authorDto.add(linkTo(methodOn(AuthorController.class).update(authorDto.getId(), authorDto)).withRel("update"));
        authorDto.add(linkTo(methodOn(AuthorController.class).deleteById(authorDto.getId())).withRel("delete"));
        authorDto.add(linkTo(methodOn(NewsController.class).create(null)).withRel("create news"));
        authorDto.add(linkTo(methodOn(NewsController.class).getAllByAuthorId(authorDto.getId(), 20, 1, null)).withRel("get news by this").expand());

        if (withLinkToGetAll) {
            authorDto.add(linkTo(methodOn(AuthorController.class).getAllByCriteria(20, 1, null, null, null)).withRel("Get all authors").expand(null, null, null));
            authorDto.add(linkTo(methodOn(AuthorController.class).getAllWithNewsCount(20, 1)).withRel("Get all authors with news count").expand());
        }
    }

    public static CollectionModel<AuthorDto> getCollectionModelWithHateoas(Page<AuthorDto> dtos, int limit, int offset, String sortBy, String name, Long newsId) {
        dtos.getContent().forEach(a -> addHateoas(a, false));

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(AuthorController.class).getAllByCriteria(limit, 1, null, null , null)).withSelfRel().expand(sortBy, name, newsId));

        if (dtos.hasNext()) {
            links.add(linkTo(methodOn(AuthorController.class).getAllByCriteria(limit, offset+1, null, null, null)).withRel("next page").expand(sortBy, name, newsId));
        }
        if (dtos.hasPrevious()) {
            links.add(linkTo(methodOn(AuthorController.class).getAllByCriteria(limit, offset-1, null, null, null)).withRel("previous page").expand(sortBy, name, newsId));
        }

        return CollectionModel.of(dtos.getContent(), links);
    }

    public static CollectionModel<AuthorDto> getCollectionModelWithHateoas(Page<AuthorDto> dtos, int limit, int offset) {
        dtos.getContent().forEach(a -> addHateoas(a, false));
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(AuthorController.class).getAllWithNewsCount(limit, 1)).withSelfRel());

        if (dtos.hasNext()) {
            links.add(linkTo(methodOn(AuthorController.class).getAllWithNewsCount(limit, offset+1)).withRel("next page").expand());
        }
        if (dtos.hasPrevious()) {
            links.add(linkTo(methodOn(AuthorController.class).getAllWithNewsCount(limit, offset-1)).withRel("previous page").expand());
        }

        return CollectionModel.of(dtos.getContent(), links);

    }
}
