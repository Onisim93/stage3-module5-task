package com.mjc.school.service.aspect;

import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.validation.AuthorValidator;
import com.mjc.school.service.validation.CommentValidator;
import com.mjc.school.service.validation.NewsValidator;
import com.mjc.school.service.validation.TagValidator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ValidationAspect {
    private final NewsValidator newsValidator;
    private final AuthorValidator authorValidator;
    private final TagValidator tagValidator;

    private final CommentValidator commentValidator;

    @Autowired
    public ValidationAspect(NewsValidator newsValidator, AuthorValidator authorValidator, TagValidator tagValidator, CommentValidator commentValidator) {
        this.newsValidator = newsValidator;
        this.authorValidator = authorValidator;
        this.tagValidator = tagValidator;
        this.commentValidator = commentValidator;
    }


    @Pointcut("@annotation(com.mjc.school.service.aspect.annotation.EntityValidate)")
    public void validate(){}


    @Before("validate()")
    public void entityValidate(JoinPoint joinPoint) {
        Object arg = joinPoint.getArgs()[0];

        if (arg instanceof NewsDto newsDto) {
            if (!newsValidator.isNew(newsDto)) {
                newsValidator.validateId(newsDto.getId());
            }
            newsValidator.validate(newsDto);
        } else if (arg instanceof CommentDto commentDto) {
            if (!commentValidator.isNew(commentDto)) {
                commentValidator.validateId(commentDto.getId());
            }
            commentValidator.validate(commentDto);

        } else if (arg instanceof AuthorDto authorDto) {
            if (!authorValidator.isNew(authorDto)) {
                authorValidator.validateId(authorDto.getId());
            }
            authorValidator.validate(authorDto);
        }
        else if (arg instanceof TagDto tagDto) {
            if (!tagValidator.isNew(tagDto)) {
                tagValidator.validateId(tagDto.getId());
            }
            tagValidator.validate(tagDto);
        }
    }
}
