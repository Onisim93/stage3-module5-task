package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsRestController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/news")
public class NewsControllerImpl implements NewsRestController {

    private final NewsService newsService;

    public NewsControllerImpl(NewsService newsService) {
        this.newsService = newsService;
    }


    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<NewsDto> readById(@PathVariable Long id) {
        return new ResponseEntity<>(newsService.readById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<NewsDto> create(@RequestBody NewsDto createRequest) {
        return new ResponseEntity<>(newsService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<NewsDto> update(@PathVariable Long id, @RequestBody NewsDto updateRequest) {
        return new ResponseEntity<>(newsService.update(updateRequest), HttpStatus.OK);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    public ResponseEntity<NewsDto> patch(@PathVariable Long id, @RequestBody NewsDto patchedRequest) {
        return new ResponseEntity<>(newsService.patch(patchedRequest), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) int limit,
            @RequestParam(defaultValue = "1", required = false) int offset,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(required = false) String tagIds,
            @RequestParam(required = false) String tagNames,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName) {

        return new ResponseEntity<>(newsService.getAllByCriteria(limit, offset, sortBy, tagIds, tagNames, content, title, authorName).getContent(), HttpStatus.OK);
    }
}
