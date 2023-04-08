package com.mjc.school.controller.impl;

import com.mjc.school.controller.AuthorRestController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1.0/author")
@Api(value = "Operations for creating, updating, retrieving and deleting authors in the application")
public class AuthorController implements AuthorRestController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View all authors", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all authors"),
            @ApiResponse(code = 400, message = "Input parameters are wrong"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public List<AuthorDto> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String newsId) {

        return authorService.getAllByCriteria(limit, offset, sortBy, newsId, name).getContent();
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get author by id", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved an author"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public AuthorDto readById(@PathVariable Long id) {
        return authorService.readById(id);
    }

    @Override
    @ApiOperation(value = "Create a piece of author", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a piece of author"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@RequestBody AuthorDto createRequest) {
        return authorService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update author", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated author"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public AuthorDto update(@PathVariable Long id, @RequestBody AuthorDto updateRequest) {
        return authorService.update(updateRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Patch author", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully patched author"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public AuthorDto patch(@PathVariable Long id, @RequestBody AuthorDto patchedRequest) {
        return authorService.patch(patchedRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete author by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted author"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }
}
