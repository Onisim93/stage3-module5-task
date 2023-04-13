package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsRestController;
import com.mjc.school.controller.util.hateoas.NewsHateoasUtil;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDto;
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
@Api(value = "Operations for creating, updating, retrieving and deleting news in the application")
public class NewsController implements NewsRestController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }


    @Override
    @ApiOperation(value = "Get news by id", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved a news"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(value = "/news/{id:\\d+}")
    public NewsDto readById(@PathVariable Long id) {
        NewsDto dto = newsService.readById(id);
        NewsHateoasUtil.addHateoas(dto, true);
        return dto;
    }

    @Override
    @ApiOperation(value = "Create new news", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created news"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping("/news")
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto create(@RequestBody NewsDto createRequest) {
        NewsDto created = newsService.create(createRequest);
        NewsHateoasUtil.addHateoas(created, true);
        return created;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update news", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated news"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PatchMapping(value = "/news/{id:\\d+}")
    public NewsDto update(@PathVariable Long id, @RequestBody NewsDto updateRequest) {
        updateRequest.setId(id);
        NewsDto updated = newsService.update(updateRequest);
        NewsHateoasUtil.addHateoas(updated, true);
        return updated;
    }


    @Override
    @DeleteMapping(value = "/news/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete news by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted news"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 400, message = "Input arguments exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/news")
    public CollectionModel<NewsDto> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(name = "sort_by",defaultValue = "created::desc", required = false) String sortBy,
            @RequestParam(name="tag_ids", required = false) String tagIds,
            @RequestParam(name="tag_names", required = false) String tagNames,
            @RequestParam(name="content", required = false) String content,
            @RequestParam(name="title", required = false) String title,
            @RequestParam(name="author_name", required = false) String authorName) {
        Page<NewsDto> newsDtoPage = newsService.getAllByCriteria(limit, offset, sortBy, tagIds, tagNames, content, title, authorName);

        return NewsHateoasUtil.getAllWithHateoas(newsDtoPage, limit, offset, sortBy, tagIds, tagNames, content, title, authorName);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/authors/{authorId:\\d+}/news")
    @ApiOperation(value = "View all news by author id", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news by author id"),
            @ApiResponse(code = 400, message = "Input arguments exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public CollectionModel<NewsDto> getAllByAuthorId(
            @PathVariable Long authorId,
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(name = "sort_by",defaultValue = "created::desc", required = false) String sortBy) {


        return NewsHateoasUtil.getAllByAuthorIdWithHateoas(newsService.getAllByAuthorId(authorId, limit, offset, sortBy), authorId, limit, offset, sortBy);
    }
}
