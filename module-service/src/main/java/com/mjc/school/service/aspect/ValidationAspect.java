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
        boolean isUpdateMethod = joinPoint.getSignature().getName().equalsIgnoreCase("update");


        if (arg instanceof NewsDto newsDto) {
            newsValidate(newsDto, isUpdateMethod);
        } else if (arg instanceof CommentDto commentDto) {
            commentValidate(commentDto, isUpdateMethod);
        } else if (arg instanceof AuthorDto authorDto) {
            authorValidate(authorDto, isUpdateMethod);
        }
        else if (arg instanceof TagDto tagDto) {
           tagValidate(tagDto, isUpdateMethod);
        }
    }


    private void newsValidate(NewsDto newsDto, boolean isUpdate) {
        if (isUpdate) newsValidator.validate(newsDto);
        else newsValidator.validateUpdatedDto(newsDto);
    }

    private void authorValidate(AuthorDto authorDto, boolean isUpdate) {
        if (isUpdate) authorValidator.validateUpdatedDto(authorDto);
        else authorValidator.validate(authorDto);
    }

    private void tagValidate(TagDto tagDto, boolean isUpdate) {
        if (isUpdate) tagValidator.validateUpdatedDto(tagDto);
        else tagValidator.validate(tagDto);
    }

    private void commentValidate(CommentDto commentDto, boolean isUpdate) {
        if (isUpdate) commentValidator.validateUpdatedDto(commentDto);
        else commentValidator.validate(commentDto);
    }
}
