package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.CommentModel;
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
public interface CommentRepository extends JpaRepository<CommentModel, Long>, JpaSpecificationExecutor<CommentModel> {
    @Transactional
    @Modifying
    @Query("DELETE FROM CommentModel u WHERE u.id=:id")
    int delete(@Param("id") long id);

    Page<CommentModel> findAll(@Nullable Specification<CommentModel> spec, @NonNull Pageable pageable);

    List<CommentModel> findAllByNewsId(Long newsId);
}
