package com.mjc.school.controller.util.hateoas;

import com.mjc.school.controller.impl.TagController;
import com.mjc.school.service.dto.TagDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class TagHateoasUtil {

    private TagHateoasUtil() {}
    public static void addHateoas(TagDto tagDto, boolean withLinkToGetAll) {
        tagDto.add(linkTo(methodOn(TagController.class).readById(tagDto.getId())).withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class).update(tagDto.getId(), tagDto)).withRel("update"));
        tagDto.add(linkTo(methodOn(TagController.class).deleteById(tagDto.getId())).withRel("delete"));
        if (withLinkToGetAll) {
            tagDto.add(linkTo(methodOn(TagController.class).getAllByCriteria(20, 1, null, null, null)).withRel("Get all authors").expand(null, null, null));
        }
    }

    public static CollectionModel<TagDto> getCollectionModelWithHateoas(Page<TagDto> dtos, int limit, int offset, String sortBy, String name, Long newsId) {
        dtos.getContent().forEach(a -> addHateoas(a, false));

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(TagController.class).getAllByCriteria(limit, 1, null, null , null)).withSelfRel().expand(sortBy, name, newsId));

        if (dtos.hasNext()) {
            links.add(linkTo(methodOn(TagController.class).getAllByCriteria(limit, offset+1, null, null, null)).withRel("next page").expand(sortBy, name, newsId));
        }
        if (dtos.hasPrevious()) {
            links.add(linkTo(methodOn(TagController.class).getAllByCriteria(limit, offset-1, null, null, null)).withRel("previous page").expand(sortBy, name, newsId));
        }

        return CollectionModel.of(dtos.getContent(), links);
    }

    public static CollectionModel<TagDto> getCollectionModelWithHateoas(Page<TagDto> dtos, Long newsId,  int limit, int offset, String sortBy) {
        dtos.getContent().forEach(a -> addHateoas(a, false));

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(TagController.class).getAllByNewsId(newsId, limit, 1, sortBy)).withSelfRel().expand());

        if (dtos.hasNext()) {
            links.add(linkTo(methodOn(TagController.class).getAllByNewsId(newsId, limit, offset+1, sortBy)).withRel("next page").expand());
        }
        if (dtos.hasPrevious()) {
            links.add(linkTo(methodOn(TagController.class).getAllByNewsId(newsId, limit, offset-1, sortBy)).withRel("previous page").expand());
        }

        return CollectionModel.of(dtos.getContent(), links);
    }
}
