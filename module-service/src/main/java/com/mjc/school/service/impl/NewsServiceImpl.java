package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.aspect.annotation.EntityValidate;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.specifications.NewsSpecifications;
import com.mjc.school.service.util.SortUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        isAuthorIdExists(createRequest.getAuthorId());
        NewsModel model = NewsMapper.INSTANCE.toModel(createRequest);
        model.setTags(getOrCreateTags(createRequest.getTagNames()));

        return NewsMapper.INSTANCE.toDto(newsRepository.save(model));
    }

    @Override
    @EntityValidate
    @Transactional
    public NewsDto update(NewsDto updateRequest) {
        NewsModel model = newsRepository.findById(updateRequest.getId()).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getId())));

        if (updateRequest.getTitle() != null) {
            model.setTitle(updateRequest.getTitle());
        }
        if (updateRequest.getContent() != null) {
            model.setContent(updateRequest.getContent());
        }
        if (updateRequest.getTagNames() != null) {
            model.setTags(getOrCreateTags(updateRequest.getTagNames()));
        }
        if (updateRequest.getAuthorId() != null) {
            model.setAuthor(authorRepository.findById(updateRequest.getAuthorId()).orElseThrow(
                    ()->new NoSuchEntityException(String.format(ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getAuthorId()))));
        }

        newsRepository.flush();
        return NewsMapper.INSTANCE.toDto(model);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return newsRepository.delete(id) != 0;
    }

    @Override
    public Page<NewsDto> getAllByCriteria(int limit, int offset, String sortBy, String tagIds, String tagNames, String content, String title, String authorName) {
        Pageable pageable = PageRequest.of(offset -1, limit, SortUtils.getSort(sortBy));

        Specification<NewsModel> specs = filterByCriteria(tagIds, tagNames, content, title, authorName);
        Page<NewsModel> listModels = newsRepository.findAll(specs, pageable);
        List<NewsDto> listDtos = NewsMapper.INSTANCE.toListDto(listModels.getContent());

        return new PageImpl<>(listDtos, pageable, listModels.getTotalElements());
    }

    @Override
    public Page<NewsDto> getAllByAuthorId(Long authorId, int limit, int offset, String sortBy) {
        isAuthorIdExists(authorId);
        Pageable pageable = PageRequest.of(offset -1, limit, SortUtils.getSort(sortBy));

        Page<NewsModel> listModels = newsRepository.findAllByAuthorId(authorId, pageable);
        List<NewsDto> listDtos = NewsMapper.INSTANCE.toListDto(listModels.getContent());

        return new PageImpl<>(listDtos, pageable, listModels.getTotalElements());
    }


    private List<TagModel> getOrCreateTags(List<String> tagNames) {
        if (tagNames == null) {
            return new ArrayList<>();
        }
        return tagNames.stream().map(tagName -> {
            TagModel tag = tagRepository.findByName(tagName);
            if (tag == null) {
                tag = tagRepository.save(new TagModel(tagName));
            }
            return tag;
        }).toList();
    }

    private void isAuthorIdExists(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new NoSuchEntityException(String.format(ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), authorId));
        }
    }

    private Specification<NewsModel> filterByCriteria(String tagIds, String tagNames, String content, String title, String authorName) {
        Specification<NewsModel> resultSpecs = null;

        if (tagIds != null && !tagIds.isBlank()) {
            resultSpecs = NewsSpecifications.hasTagIdsLike(tagIds);
        }

        if (tagNames != null && !tagNames.isBlank()) {
            Specification<NewsModel> tagNamesSpec = NewsSpecifications.hasTagNamesLike(tagNames);
            resultSpecs = tagNamesSpec.and(resultSpecs);
        }

        if (content != null && !content.isBlank()) {
            Specification<NewsModel> contentSpec = NewsSpecifications.hasContentLike(content.trim());
            resultSpecs = contentSpec.and(resultSpecs);
        }

        if (title != null && !title.isBlank()) {
            Specification<NewsModel> titleSpec = NewsSpecifications.hasTitleLike(title.trim());
            resultSpecs = titleSpec.and(resultSpecs);
        }

        if (authorName != null && !authorName.isBlank()) {
            Specification<NewsModel> authorNameSpec = NewsSpecifications.hasAuthorNameLike(authorName.trim());
            resultSpecs = authorNameSpec.and(resultSpecs);
        }

        return resultSpecs;
    }
}
