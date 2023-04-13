package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagRestController;
import com.mjc.school.controller.util.hateoas.TagHateoasUtil;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDto;
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
@RequestMapping(value = "api/v1.0")
@Api(value = "Operations for creating, updating, retrieving and deleting tags in the application")
public class TagController implements TagRestController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    @ApiOperation(value = "Get tag by id", response = TagDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a tag"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/tags/{id:\\d+}")
    public TagDto readById(@PathVariable Long id) {
        TagDto tag = tagService.readById(id);
        TagHateoasUtil.addHateoas(tag, true);
        return tag;
    }

    @Override
    @ApiOperation(value = "Create new tag", response = TagDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created tag"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/tags")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto createRequest) {
        TagDto created = tagService.create(createRequest);
        TagHateoasUtil.addHateoas(created, true);
        return created;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update tag", response = TagDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated tag"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PatchMapping(value = "/tags/{id:\\d+}")
    public TagDto update(@PathVariable Long id, @RequestBody TagDto updateRequest) {
        updateRequest.setId(id);
        TagDto updated = tagService.update(updateRequest);
        TagHateoasUtil.addHateoas(updated, true);
        return updated;
    }

    @Override
    @DeleteMapping(value = "/tags/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete tag by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted tag"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View all tags", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all tags"),
            @ApiResponse(code = 400, message = "Input arguments exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/tags")
    public CollectionModel<TagDto> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(name = "sort_by", defaultValue = "created::desc", required = false) String sortBy,
            @RequestParam(name="name", required = false) String name,
            @RequestParam(name="news_id", required = false) Long newsId) {
        Page<TagDto> tagDtoPage = tagService.getAllByCriteria(limit, offset, sortBy, newsId, name);


        return TagHateoasUtil.getCollectionModelWithHateoas(tagDtoPage, limit, offset, sortBy, name, newsId);
    }

    @Override
    @ApiOperation(value = "View all tags by news id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all tags"),
            @ApiResponse(code = 400, message = "Input arguments exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/news/{newsId:\\d+}/tags")
    public CollectionModel<TagDto> getAllByNewsId(
            @PathVariable Long newsId,
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(name = "sort_by", defaultValue = "name::asc", required = false) String sortBy) {
        Page<TagDto> tagDtoPage = tagService.getAllByCriteria(limit, offset, sortBy, newsId, null);

        return TagHateoasUtil.getCollectionModelWithHateoas(tagDtoPage, newsId, limit, offset, sortBy);
    }
}
