package com.mjc.school.service.validation;

import com.mjc.school.service.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentValidator extends BaseValidator<CommentDto> {
    @Override
    public void validate(CommentDto commentDto) {
        validateString(commentDto.getContent(), Constants.COMMENT_CONTENT.getDescription(), Constants.COMMENT_CONTENT.getMinValue(), Constants.COMMENT_CONTENT.getMaxValue());
        validateNumber(commentDto.getNewsId(), Constants.NEWS_ID.getDescription());
    }

    @Override
    public void validateUpdatedDto(CommentDto objectDto) {
        validateId(objectDto.getId());
        if (objectDto.getContent() != null) {
            validateString(objectDto.getContent(), Constants.COMMENT_CONTENT.getDescription(), Constants.COMMENT_CONTENT.getMinValue(), Constants.COMMENT_CONTENT.getMaxValue());
        }
        if (objectDto.getNewsId() != null) {
            validateNumber(objectDto.getNewsId(), Constants.NEWS_ID.getDescription());
        }
    }

    @Override
    public void validateId(Long id) {
        validateNumber(id, Constants.COMMENT_ID.getDescription());
    }
}
