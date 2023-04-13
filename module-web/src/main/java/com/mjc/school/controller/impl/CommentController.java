package com.mjc.school.controller.impl;

import com.mjc.school.controller.CommentRestController;
import com.mjc.school.controller.util.hateoas.CommentHateoasUtil;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1.0/news/{newsId:\\d+}}")
@Api(value = "Operations for creating, updating, retrieving and deleting comments in the application")
public class CommentController implements CommentRestController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Override
    @GetMapping(value = "/comments/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get comment by id", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a comment"),
            @ApiResponse(code = 400, message = "Input arguments exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public CommentDto readById(@PathVariable Long newsId, @PathVariable Long id) {
        CommentDto comment = commentService.readById(id);
        CommentHateoasUtil.addHateoas(comment, true);
        return comment;
    }

    @ApiOperation(value = "Get all comments by news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a comment"),
            @ApiResponse(code = 400, message = "Input arguments exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CommentDto> getAllByNewsId(
            @PathVariable Long newsId,
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(name = "sort_by", defaultValue = "created::desc", required = false) String sortBy,
            @RequestParam(name = "content", required = false) String content
            ) {
        Page<CommentDto> commentDtoPage = commentService.getAllByNewsId(newsId, limit, offset, sortBy, content);
        return CommentHateoasUtil.getCollectionModelWithHateoas(commentDtoPage,newsId, limit, offset, sortBy, content);
    }

    @Override
    @ApiOperation(value = "Create new comment", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created comment"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable Long newsId, @RequestBody CommentDto createRequest) {
        createRequest.setNewsId(newsId);
        CommentDto created = commentService.create(createRequest);
        CommentHateoasUtil.addHateoas(created, true);
        return created;
    }


    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update comment", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated comment"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PatchMapping(value = "/comments/{id:\\d+}")
    public CommentDto update(@PathVariable Long id, @RequestBody CommentDto updateRequest) {
        updateRequest.setId(id);
        CommentDto updated = commentService.update(updateRequest);
        CommentHateoasUtil.addHateoas(updated, true);
        return updated;
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete comment by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted comment"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/comments/{id:\\d+}")
    public ResponseEntity<Void> deleteById(@PathVariable Long newsId, @PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
