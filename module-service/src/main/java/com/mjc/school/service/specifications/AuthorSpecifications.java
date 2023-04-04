package com.mjc.school.service.specifications;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class AuthorSpecifications {
    private AuthorSpecifications(){}
    public static Specification<AuthorModel> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<AuthorModel> hasNewsIdLike(Long newsId) {
        return (root, query, cb) -> {
            Join<AuthorModel, NewsModel> news = root.join("news");
            return cb.equal(news.get("id"), newsId);
        };
    }
}
