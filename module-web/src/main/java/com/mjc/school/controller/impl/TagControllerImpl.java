package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagRestController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tag")
public class TagControllerImpl implements TagRestController {

    private final TagService tagService;

    public TagControllerImpl(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<TagDto> readById(@PathVariable Long id) {
        return new ResponseEntity<>(tagService.readById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<TagDto> create(@RequestBody TagDto createRequest) {
        return new ResponseEntity<>(tagService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<TagDto> update(@PathVariable Long id, @RequestBody TagDto updateRequest) {
        return new ResponseEntity<>(tagService.update(updateRequest), HttpStatus.OK);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    public ResponseEntity<TagDto> patch(@PathVariable Long id, @RequestBody TagDto patchedRequest) {
        return new ResponseEntity<>(tagService.patch(patchedRequest), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity deleteById(Long id) {
        tagService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<TagDto>> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) int limit,
            @RequestParam(defaultValue = "1", required = false) int offset,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String newsId) {
        return new ResponseEntity<>(tagService.getAllByCriteria(limit, offset, sortBy, newsId, name).getContent(), HttpStatus.OK);
    }
}
