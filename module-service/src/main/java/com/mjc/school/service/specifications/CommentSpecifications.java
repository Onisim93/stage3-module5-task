package com.mjc.school.service.specifications;

import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class CommentSpecifications {
    private CommentSpecifications(){}
    public static Specification<CommentModel> hasContentLike(String content) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("content"), "%" + content + "%");
    }

    public static Specification<CommentModel> hasNewsIdLike(Long newsId) {
        return (root, query, cb) -> {
            Join<CommentModel, NewsModel> news = root.join("news");
            return cb.equal(news.get("id"), newsId);
        };
    }
}
