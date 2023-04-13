package com.mjc.school.service.validation;

import com.mjc.school.service.dto.NewsDto;
import org.springframework.stereotype.Component;

@Component
public class NewsValidator extends BaseValidator<NewsDto> {

    @Override
    public void validate(NewsDto newsDto) {
        validateString(newsDto.getTitle(), Constants.NEWS_TITLE.getDescription(), Constants.NEWS_TITLE.getMinValue(), Constants.NEWS_TITLE.getMaxValue());
        validateString(newsDto.getContent(), Constants.NEWS_CONTENT.getDescription(), Constants.NEWS_CONTENT.getMinValue(), Constants.NEWS_CONTENT.getMaxValue());
    }

    @Override
    public void validateUpdatedDto(NewsDto objectDto) {
        validateId(objectDto.getId());
        if (objectDto.getTitle() != null) {
            validateString(objectDto.getTitle(), Constants.NEWS_TITLE.getDescription(), Constants.NEWS_TITLE.getMinValue(), Constants.NEWS_TITLE.getMaxValue());
        }
        if (objectDto.getContent() != null) {
            validateString(objectDto.getContent(), Constants.NEWS_CONTENT.getDescription(), Constants.NEWS_CONTENT.getMinValue(), Constants.NEWS_CONTENT.getMaxValue());
        }
    }

    @Override
    public void validateId(Long id) {
        validateNumber(id, Constants.NEWS_ID.getDescription());
    }
}
