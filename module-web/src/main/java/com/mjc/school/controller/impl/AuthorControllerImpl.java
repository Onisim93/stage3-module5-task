package com.mjc.school.controller.impl;

import com.mjc.school.controller.AuthorRestController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/author")
public class AuthorControllerImpl implements AuthorRestController {

    private final AuthorService authorService;

    public AuthorControllerImpl(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) int limit,
            @RequestParam(defaultValue = "1", required = false) int offset,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String newsId) {

        return new ResponseEntity<>(authorService.getAllByCriteria(limit, offset, sortBy, newsId, name).getContent(), HttpStatus.OK);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<AuthorDto> readById(@PathVariable Long id) {
        return new ResponseEntity<>(authorService.readById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto createRequest) {
        return new ResponseEntity<>(authorService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<AuthorDto> update(@PathVariable Long id, @RequestBody AuthorDto updateRequest) {
        return new ResponseEntity<>(authorService.update(updateRequest), HttpStatus.OK);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    public ResponseEntity<AuthorDto> patch(@PathVariable Long id, @RequestBody AuthorDto patchedRequest) {
        return new ResponseEntity<>(authorService.patch(patchedRequest), HttpStatus.OK);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
