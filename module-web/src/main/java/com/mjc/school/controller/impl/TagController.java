package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagRestController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1.0/tag")
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
    @GetMapping(value = "/{id:\\d+}")
    public TagDto readById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @Override
    @ApiOperation(value = "Create a piece of tag", response = TagDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a piece of tag"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto createRequest) {
        return tagService.create(createRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update tag", response = TagDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated tag"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/{id:\\d+}")
    public TagDto update(@PathVariable Long id, @RequestBody TagDto updateRequest) {
        return tagService.update(updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Patch tag", response = TagDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully patched tag"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PatchMapping(value = "/{id:\\d+}")
    public TagDto patch(@PathVariable Long id, @RequestBody TagDto patchedRequest) {
        return tagService.patch(patchedRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete tag by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted tag"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View all tags", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all tags"),
            @ApiResponse(code = 400, message = "Input parameters are wrong"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public List<TagDto> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String newsId) {
        return tagService.getAllByCriteria(limit, offset, sortBy, newsId, name).getContent();
    }
}
