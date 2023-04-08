package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsRestController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1.0/news")
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
    @GetMapping(value = "/{id:\\d+}")
    public NewsDto readById(@PathVariable Long id) {
        return newsService.readById(id);
    }

    @Override
    @ApiOperation(value = "Create a piece of news", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created a piece of news"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto create(@RequestBody NewsDto createRequest) {
        return newsService.create(createRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update news", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated news"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping(value = "/{id:\\d+}")
    public NewsDto update(@PathVariable Long id, @RequestBody NewsDto updateRequest) {
        return newsService.update(updateRequest);
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Patch news", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully patched news"),
            @ApiResponse(code = 400, message = "Entity validation exception"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PatchMapping(value = "/{id:\\d+}")
    public NewsDto patch(@PathVariable Long id, @RequestBody NewsDto patchedRequest) {
        return newsService.patch(patchedRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete news by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted news"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })    public void deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @Override
    @ApiOperation(value = "View all news", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all news"),
            @ApiResponse(code = 400, message = "Input parameters are wrong"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public List<NewsDto> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) @Min(1) int limit,
            @RequestParam(defaultValue = "1", required = false) @Min(1) int offset,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(required = false) String tagIds,
            @RequestParam(required = false) String tagNames,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName) {

        return newsService.getAllByCriteria(limit, offset, sortBy, tagIds, tagNames, content, title, authorName).getContent();
    }
}
