package com.mjc.school.service.aspect;

import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.exception.ValidatorException;
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

import java.util.regex.Pattern;


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

    @Pointcut("@annotation(com.mjc.school.service.aspect.annotation.IdValidate)")
    public void idValidate(){}

    @Before("idValidate()")
    public void isIdValid(JoinPoint joinPoint) {
        Object arg = joinPoint.getArgs()[0];
        if (arg instanceof String id) {
            String[] ids = id.split(",");
            Pattern pattern = Pattern.compile("\\d");
            for (String str : ids) {
                if (!pattern.matcher(str.trim()).matches()) {
                    throw new ValidatorException(String.format(ServiceErrorCode.VALIDATE_INT_VALUE.getMessage(), str.trim()));
                }
            }
        }

    }


    @Before("validate()")
    public void entityValidate(JoinPoint joinPoint) {
        Object arg = joinPoint.getArgs()[0];
        boolean isUpdateMethod = joinPoint.getSignature().getName().equalsIgnoreCase("update");


        if (arg instanceof NewsDto newsDto) {
            if (!isUpdateMethod) newsValidator.validate(newsDto);
            else newsValidator.validateUpdatedDto(newsDto);

        } else if (arg instanceof CommentDto commentDto) {
            if (!isUpdateMethod) commentValidator.validate(commentDto);
            else commentValidator.validateUpdatedDto(commentDto);

        } else if (arg instanceof AuthorDto authorDto) {
            if (!isUpdateMethod) authorValidator.validate(authorDto);
            else authorValidator.validateUpdatedDto(authorDto);
        }
        else if (arg instanceof TagDto tagDto) {
            if(!isUpdateMethod) tagValidator.validate(tagDto);
            else tagValidator.validateUpdatedDto(tagDto);
        }
    }
}
