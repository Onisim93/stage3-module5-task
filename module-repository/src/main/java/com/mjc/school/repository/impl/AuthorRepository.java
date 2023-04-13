package com.mjc.school.repository.impl;

import com.mjc.school.repository.model.AuthorModel;
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
public interface AuthorRepository extends JpaRepository<AuthorModel, Long>, JpaSpecificationExecutor<AuthorModel> {
    @Transactional
    @Modifying
    @Query("DELETE FROM AuthorModel u WHERE u.id=:id")
    int delete(@Param("id") long id);


    Page<AuthorModel> findAll(@Nullable Specification<AuthorModel> spec, @NonNull Pageable pageable);

}
