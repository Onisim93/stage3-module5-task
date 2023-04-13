package com.mjc.school.controller.impl;

import com.mjc.school.controller.AuthorRestController;
import com.mjc.school.controller.util.hateoas.AuthorHateoasUtil;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDto;
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
            @ApiResponse(code = 400, message = "Input arguments exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/authors")
    public CollectionModel<AuthorDto> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(name = "sort_by", defaultValue = "created::desc", required = false) String sortBy,
            @RequestParam(name="name", required = false) String name,
            @RequestParam(name = "news_id", required = false) Long newsId) {

        Page<AuthorDto> resultList = authorService.getAllByCriteria(limit, offset, sortBy, newsId, name, false);

        return AuthorHateoasUtil.getCollectionModelWithHateoas(resultList, limit, offset, sortBy, name, newsId);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get author by news id", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved an author"),
            @ApiResponse(code = 400, message = "Input arguments exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/news/{newsId:\\d+}/authors")
    public AuthorDto getByNewsId(@PathVariable Long newsId) {
        AuthorDto author = authorService.getByNewsId(newsId);
        AuthorHateoasUtil.addHateoas(author, true);
        return author;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/authors/with-amount-news")
    @ApiOperation(value = "Get authors with amount news", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved authors"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public CollectionModel<AuthorDto> getAllWithNewsCount(
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset)
             {

        Page<AuthorDto> resultList = authorService.getAllByCriteria(limit, offset, "newsCount::desc", null, null, true);

        return AuthorHateoasUtil.getCollectionModelWithHateoas(resultList, limit, offset);
    }

    @Override
    @GetMapping(value = "/authors/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get author by id", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved an author"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public AuthorDto readById(@PathVariable Long id) {
        AuthorDto authorDto = authorService.readById(id);
        AuthorHateoasUtil.addHateoas(authorDto, true);
        return authorDto;
    }

    @Override
    @ApiOperation(value = "Create new author", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created author"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@RequestBody AuthorDto createRequest) {
        AuthorDto created = authorService.create(createRequest);
        AuthorHateoasUtil.addHateoas(created, true);
        return created;
    }

    @Override
    @PatchMapping(value = "/authors/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update author", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated author"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public AuthorDto update(@PathVariable Long id, @RequestBody AuthorDto updateRequest) {
        updateRequest.setId(id);
        AuthorDto updated = authorService.update(updateRequest);
        AuthorHateoasUtil.addHateoas(updated, true);
        return updated;
    }

    @Override
    @DeleteMapping(value = "/authors/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete author by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted author"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
