package com.mjc.school.controller.impl;

import com.mjc.school.controller.CommentRestController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/comment")
public class CommentControllerImpl implements CommentRestController {

    private final CommentService commentService;

    public CommentControllerImpl(CommentService commentService) {
        this.commentService = commentService;
    }


    @Override
    @GetMapping(value = "/{id:\\d+}")
    public ResponseEntity<CommentDto> readById(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.readById(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<CommentDto> create(@RequestBody CommentDto createRequest) {
        return new ResponseEntity<>(commentService.create(createRequest), HttpStatus.CREATED);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto updateRequest) {
        return new ResponseEntity<>(commentService.update(updateRequest), HttpStatus.OK);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    public ResponseEntity<CommentDto> patch(@PathVariable Long id, @RequestBody CommentDto patchedRequest) {
        return new ResponseEntity<>(commentService.patch(patchedRequest), HttpStatus.OK);

    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllByCriteria(
            @RequestParam(defaultValue = "20", required = false) int limit,
            @RequestParam(defaultValue = "1", required = false) int offset,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String newsId) {
        return new ResponseEntity<>(commentService.getAllByCriteria(limit, offset, sortBy, content, newsId).getContent(), HttpStatus.OK);
    }
}
