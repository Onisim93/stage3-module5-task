package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.aspect.annotation.EntityValidate;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.specifications.NewsSpecifications;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final TagRepository tagRepository;
    private final AuthorRepository authorRepository;

    public NewsServiceImpl(NewsRepository newsRepository, TagRepository tagRepository, AuthorRepository authorRepository) {
        this.newsRepository = newsRepository;
        this.tagRepository = tagRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public List<NewsDto> readAll() {
        return NewsMapper.INSTANCE.toListDto(newsRepository.findAll());
    }

    @Override
    public NewsDto readById(Long id) {
        return NewsMapper.INSTANCE.toDto(newsRepository.findById(id).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), id))));
    }

    @Override
    @EntityValidate
    @Transactional
    public NewsDto create(NewsDto createRequest) {
        isTagsExists(createRequest.getTagIds());
        isAuthorIdExists(createRequest.getAuthorId());
        return NewsMapper.INSTANCE.toDto(newsRepository.saveAndFlush(NewsMapper.INSTANCE.toModel(createRequest)));
    }

    @Override
    @EntityValidate
    @Transactional
    public NewsDto update(NewsDto updateRequest) {
        isTagsExists(updateRequest.getTagIds());
        isAuthorIdExists(updateRequest.getAuthorId());
        return NewsMapper.INSTANCE.toDto(newsRepository.saveAndFlush(NewsMapper.INSTANCE.toModel(updateRequest)));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return newsRepository.delete(id) != 0;
    }

    @Override
    public Page<NewsDto> getAllByCriteria(int page, int limit, String sortBy, List<String> filterParams) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page-1, limit, sort);

        Specification<NewsModel> specs = filterByCriteria(filterParams);
        Page<NewsModel> listModels = newsRepository.findAll(specs, pageable);
        List<NewsDto> listDtos = NewsMapper.INSTANCE.toListDto(listModels.getContent());

        return new PageImpl<>(listDtos, pageable, listModels.getTotalElements());
    }

    @Override
    public List<NewsDto> getAllByParams(List<String> parameters) {
        return NewsMapper.INSTANCE.toListDto(newsRepository.findAll(filterByCriteria(parameters)));
    }

    private void isTagsExists(List<Long> tagIds) {
        for (Long id : tagIds) {
            if (!tagRepository.existsById(id)) {
                throw new NoSuchEntityException(String.format(ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getMessage(), id));
            }
        }
    }

    private void isAuthorIdExists(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new NoSuchEntityException(String.format(ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), authorId));
        }
    }

    private Specification<NewsModel> filterByCriteria(List<String> parameters) {
        Specification<NewsModel> resultSpecs = null;

        for (String parameter : parameters) {
            String[] param = parameter.split("=");
            Specification<NewsModel> spec = switch (param[0]) {
                case "tagIds" -> NewsSpecifications.hasTagIdsLike(Arrays.stream(param[1].split(",")).map(s -> Long.parseLong(s.trim())).toList());
                case "tagNames" -> NewsSpecifications.hasTagNamesLike(Arrays.stream(param[1].split(",")).map(String::trim).toList());
                case "content" -> NewsSpecifications.hasContentLike(param[1].trim());
                case "title" -> NewsSpecifications.hasTitleLike(param[1].trim());
                case "authorName" -> NewsSpecifications.hasAuthorNameLike(param[1].trim());
                default -> null;
            };

            resultSpecs = resultSpecs == null ? spec : resultSpecs.and(spec);

        }

        return resultSpecs;
    }
}
