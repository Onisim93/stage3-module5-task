package com.mjc.school.controller.impl;

import com.mjc.school.controller.CommentRestController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1.0/comment")
@Api(value = "Operations for creating, updating, retrieving and deleting comments in the application")
public class CommentController implements CommentRestController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get comment by id", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a comment"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public CommentDto readById(@PathVariable Long id) {
        return commentService.readById(id);
    }

    @Override
    @ApiOperation(value = "Create a piece of comment", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a piece of comment"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@RequestBody CommentDto createRequest) {
        return commentService.create(createRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update comment", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated comment"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/{id:\\d+}")
    public CommentDto update(@PathVariable Long id, @RequestBody CommentDto updateRequest) {
        return commentService.update(updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Patch comment", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully patched comment"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PatchMapping(value = "/{id:\\d+}")
    public CommentDto patch(@PathVariable Long id, @RequestBody CommentDto patchedRequest) {
        return commentService.patch(patchedRequest);

    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete comment by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted comment"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @DeleteMapping(value = "/{id:\\d+}")
    public void deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View all comments", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all comments"),
            @ApiResponse(code = 400, message = "Input parameters are wrong"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public List<CommentDto> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String newsId) {
        return commentService.getAllByCriteria(limit, offset, sortBy, content, newsId).getContent();
    }
}
