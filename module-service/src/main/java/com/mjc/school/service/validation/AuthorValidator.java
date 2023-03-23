package com.mjc.school.service.validation;

import com.mjc.school.service.dto.AuthorDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorValidator extends BaseValidator<AuthorDto> {
    @Override
    public void validate(AuthorDto authorDto) {
        validateString(authorDto.getName(), Constants.AUTHOR_NAME.getDescription(), Constants.AUTHOR_NAME.getMinValue(), Constants.AUTHOR_NAME.getMaxValue());
    }

    @Override
    public void validateId(Long id) {
        validateNumber(id, Constants.AUTHOR_ID.getDescription());
    }
}
