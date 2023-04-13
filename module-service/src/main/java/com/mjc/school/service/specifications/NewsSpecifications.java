package com.mjc.school.service.specifications;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.exception.ValidationException;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.Arrays;
import java.util.List;

public class NewsSpecifications {
    private NewsSpecifications(){}

    public static Specification<NewsModel> hasAuthorNameLike(String authorName) {
        return (root, query, cb) -> {
            Join<NewsModel, AuthorModel> author = root.join("author");
            return cb.equal(author.get("name"), authorName);
        };
    }

    public static Specification<NewsModel> hasContentLike(String content) {
        return (root, query, cb) -> cb.like(root.get("content"), "%" + content + "%");
    }

    public static Specification<NewsModel> hasTitleLike(String title) {
        return (root, query, cb) -> cb.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<NewsModel> hasTagNamesLike(String tagNames) {
        String[] tagArray = tagNames.split(",");
        List<String> tagList = Arrays.stream(tagArray).map(String::trim).toList();
        return (root, query, cb) -> {
            Join<NewsModel, TagModel> tags = root.join("tags");
            return tags.get("name").in(tagList);
        };
    }

    public static Specification<NewsModel> hasTagIdsLike(String tagIds) {
        String[] ids = tagIds.split(",");
        List<Long> tagIdsList;
        try {
            tagIdsList = Arrays.stream(ids).map(id->Long.parseLong(id.trim())).toList();
        }
        catch (NumberFormatException e) {
            throw new ValidationException(String.format(ServiceErrorCode.VALIDATE_TAG_IDS.getMessage(), tagIds));

        }

        return (root, query, cb) -> {
            Join<NewsModel, TagModel> tags = root.join("tags");
            return tags.get("id").in(tagIdsList);
        };
    }





}
