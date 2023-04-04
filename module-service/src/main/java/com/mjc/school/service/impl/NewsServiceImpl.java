package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.aspect.annotation.EntityValidate;
import com.mjc.school.service.aspect.annotation.IdValidate;
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
import java.util.stream.Collectors;

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
    public NewsDto patch(NewsDto patchRequest) {
        NewsModel model = newsRepository.findById(patchRequest.getId()).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), patchRequest.getId())));

        if (patchRequest.getTitle() != null) {
            model.setTitle(patchRequest.getTitle());
        }
        if (patchRequest.getContent() != null) {
            model.setContent(patchRequest.getContent());
        }
        if (patchRequest.getTagIds() != null) {
            isTagsExists(patchRequest.getTagIds());
            model.setTags(patchRequest.getTagIds().stream().map(TagModel::new).toList());
        }
        if (patchRequest.getAuthorId() != null) {
            isAuthorIdExists(patchRequest.getAuthorId());
            model.setAuthor(authorRepository.findById(patchRequest.getAuthorId()).orElseThrow(
                    ()->new NoSuchEntityException(String.format(ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), patchRequest.getAuthorId()))));
        }


        return NewsMapper.INSTANCE.toDto(model);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return newsRepository.delete(id) != 0;
    }

    @Override
    public Page<NewsDto> getAllByCriteria(int limit, int offset, String sortBy, String tagIds, String tagNames, String content, String title, String authorName) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(offset -1, limit, sort);

        Specification<NewsModel> specs = filterByCriteria(tagIds, tagNames, content, title, authorName);
        Page<NewsModel> listModels = newsRepository.findAll(specs, pageable);
        List<NewsDto> listDtos = NewsMapper.INSTANCE.toListDto(listModels.getContent());

        return new PageImpl<>(listDtos, pageable, listModels.getTotalElements());
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

    @IdValidate
    private Specification<NewsModel> filterByCriteria(String tagIds, String tagNames, String content, String title, String authorName) {
        Specification<NewsModel> resultSpecs = null;

        if (tagIds != null) {
            String[] ids = tagIds.split(",");
            resultSpecs = NewsSpecifications.hasTagIdsLike(Arrays.stream(ids).map(Long::parseLong).toList());
        }

        if (tagNames != null) {
            Specification<NewsModel> tagNamesSpec = NewsSpecifications.hasTagNamesLike(Arrays.stream(tagNames.split(",")).map(String::trim).toList());
            resultSpecs = resultSpecs == null ? tagNamesSpec : resultSpecs.and(tagNamesSpec);
        }

        if (content != null) {
            Specification<NewsModel> contentSpec = NewsSpecifications.hasContentLike(content);
            resultSpecs = resultSpecs == null ? contentSpec : resultSpecs.and(contentSpec);
        }

        if (title != null) {
            Specification<NewsModel> titleSpec = NewsSpecifications.hasTitleLike(title);
            resultSpecs = resultSpecs == null ? titleSpec : resultSpecs.and(titleSpec);
        }

        if (authorName != null) {
            Specification<NewsModel> authorNameSpec = NewsSpecifications.hasAuthorNameLike(authorName);
            resultSpecs = resultSpecs == null ? authorNameSpec : resultSpecs.and(authorNameSpec);

        }

        return resultSpecs;
    }
}
