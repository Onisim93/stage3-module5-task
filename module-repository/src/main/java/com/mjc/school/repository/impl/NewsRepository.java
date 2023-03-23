package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
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



@Transactional(readOnly = true)
public interface NewsRepository extends JpaRepository<NewsModel, Long>, JpaSpecificationExecutor<NewsModel> {
    @Transactional
    @Modifying
    @Query("DELETE FROM NewsModel u WHERE u.id=:id")
    int delete(@Param("id") long id);

    Page<NewsModel> findAll(@Nullable Specification<NewsModel> spec,@NonNull Pageable pageable);
}
