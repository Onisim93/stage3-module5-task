package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.TagModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface TagRepository extends JpaRepository<TagModel, Long>, JpaSpecificationExecutor<TagModel> {
    @Transactional
    @Modifying
    @Query("DELETE FROM TagModel u WHERE u.id=:id")
    int delete(@Param("id") long id);

    Page<TagModel> findAll(@Nullable Specification<TagModel> spec,@NonNull Pageable pageable);

    List<TagModel> findAllByNewsId(Long newsId);
}
