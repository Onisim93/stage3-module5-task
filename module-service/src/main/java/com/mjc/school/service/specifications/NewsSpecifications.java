package com.mjc.school.service.specifications;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.List;

public class NewsSpecifications {

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

    public static Specification<NewsModel> hasTagNamesLike(List<String> tagNames) {
        return (root, query, cb) -> {
            Join<NewsModel, TagModel> tags = root.join("tags");
            return tags.get("name").in(tagNames);
        };
    }

    public static Specification<NewsModel> hasTagIdsLike(List<Long> tagIds) {
        return (root, query, cb) -> {
            Join<NewsModel, TagModel> tags = root.join("tags");
            return tags.get("id").in(tagIds);
        };
    }





}
