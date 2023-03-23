package com.mjc.school.service.validation;

import com.mjc.school.service.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentValidator extends BaseValidator<CommentDto> {
    @Override
    public void validate(CommentDto commentDto) {
        validateString(commentDto.getContent(), Constants.COMMENT_CONTENT.getDescription(), Constants.COMMENT_CONTENT.getMinValue(), Constants.COMMENT_CONTENT.getMaxValue());
    }

    @Override
    public void validateId(Long id) {
        validateNumber(id, Constants.COMMENT_ID.getDescription());
    }
}
