package com.mjc.school.service.validation;

import com.mjc.school.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class TagValidator extends BaseValidator<TagDto> {

    @Override
    public void validate(TagDto tagDto) {
        validateString(tagDto.getName(), Constants.TAG_NAME.getDescription(), Constants.TAG_NAME.getMinValue(), Constants.TAG_NAME.getMaxValue());
    }

    @Override
    public void validateUpdatedDto(TagDto objectDto) {
        validateId(objectDto.getId());
        if (objectDto.getName() != null) {
            validateString(objectDto.getName(), Constants.TAG_NAME.getDescription(), Constants.TAG_NAME.getMinValue(), Constants.TAG_NAME.getMaxValue());
        }
    }

    @Override
    public void validateId(Long id) {
        validateNumber(id, Constants.TAG_ID.getDescription());
    }
}
