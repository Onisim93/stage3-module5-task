package com.mjc.school.service.validation;

import lombok.Getter;

@Getter
public enum Constants {
    NEWS_ID("News id", 0, 0),
    AUTHOR_ID("Author id", 0, 0),
    TAG_ID("Tag id", 0, 0),
    COMMENT_ID("Comment id", 0, 0),
    NEWS_TITLE("News title", 5, 30),
    NEWS_CONTENT("News content", 5, 255),
    AUTHOR_NAME("Author name", 3, 15),
    TAG_NAME("Tag name", 3, 15),
    COMMENT_CONTENT("Comment content", 5, 255);

    private final String description;
    private final Integer minValue;
    private final Integer maxValue;

    Constants(String description, Integer minValue, Integer maxValue) {
        this.description = description;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
