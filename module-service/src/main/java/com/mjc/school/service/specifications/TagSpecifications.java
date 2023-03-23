package com.mjc.school.service.specifications;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class TagSpecifications {
    public static Specification<TagModel> hasNameLike(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<TagModel> hasNewsIdLike(Long newsId) {
        return (root, query, cb) -> {
            Join<TagModel, NewsModel> news = root.join("news");
            return cb.equal(news.get("id"), newsId);
        };
    }
}
