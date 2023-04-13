package com.mjc.school.controller.util.hateoas;

import com.mjc.school.controller.impl.CommentController;
import com.mjc.school.service.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CommentHateoasUtil {

    private CommentHateoasUtil() {}
    public static void addHateoas(CommentDto commentDto, boolean withLinkToGetAll) {
        commentDto.add(linkTo(methodOn(CommentController.class).readById(commentDto.getNewsId(), commentDto.getId())).withSelfRel());
        commentDto.add(linkTo(methodOn(CommentController.class).update(commentDto.getId(), commentDto)).withRel("update"));
        commentDto.add(linkTo(methodOn(CommentController.class).deleteById(commentDto.getNewsId(), commentDto.getId())).withRel("delete"));
        if (withLinkToGetAll) {
            commentDto.add(linkTo(methodOn(CommentController.class).getAllByNewsId(commentDto.getNewsId(), 20, 1, null, null)).withRel("Get all comments").expand());
        }
    }

    public static CollectionModel<CommentDto> getCollectionModelWithHateoas(Page<CommentDto> dtos, Long newsId, int limit, int offset, String sortBy, String content) {
        dtos.getContent().forEach(a -> addHateoas(a, false));

        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(CommentController.class).getAllByNewsId(newsId, limit, 1, sortBy, content)).withSelfRel().expand());

        if (dtos.hasNext()) {
            links.add(linkTo(methodOn(CommentController.class).getAllByNewsId(newsId, limit, offset+1, sortBy, content)).withRel("next page").expand());
        }
        if (dtos.hasPrevious()) {
            links.add(linkTo(methodOn(CommentController.class).getAllByNewsId(newsId, limit, offset-1, sortBy, content)).withRel("previous page").expand());
        }

        return CollectionModel.of(dtos.getContent(), links);
    }
}
