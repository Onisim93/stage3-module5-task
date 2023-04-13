package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.aspect.annotation.EntityValidate;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.mapper.CommentMapper;
import com.mjc.school.service.specifications.CommentSpecifications;
import com.mjc.school.service.util.SortUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    public CommentServiceImpl(CommentRepository commentRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    @Override
    public List<CommentDto> readAll() {
        return CommentMapper.INSTANCE.toListDto(commentRepository.findAll());
    }

    @Override
    public CommentDto readById(Long id) {
        return CommentMapper.INSTANCE.toDto(commentRepository.findById(id).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST.getMessage(), id))));
    }

    @Override
    @EntityValidate
    @Transactional
    public CommentDto create(CommentDto createRequest) {
        isNewsIdExists(createRequest.getNewsId());
        return CommentMapper.INSTANCE.toDto(commentRepository.saveAndFlush(CommentMapper.INSTANCE.toModel(createRequest)));
    }

    @Override
    @EntityValidate
    @Transactional
    public CommentDto update(CommentDto updateRequest) {
        CommentModel model = commentRepository.findById(updateRequest.getId()).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getId())));

        if (updateRequest.getContent() != null) {
            model.setContent(updateRequest.getContent());
        }
        if (updateRequest.getNewsId() != null) {
            model.setNews(newsRepository.findById(updateRequest.getNewsId()).orElseThrow(
                    ()->new NoSuchEntityException(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getNewsId()))));
        }
        commentRepository.flush();
        return CommentMapper.INSTANCE.toDto(model);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return commentRepository.delete(id) != 0;
    }

    @Override
    public Page<CommentDto> getAllByNewsId(Long newsId, int limit, int offset, String sortBy, String content) {
        Pageable pageable = PageRequest.of(offset -1, limit, SortUtils.getSort(sortBy));

        Specification<CommentModel> specs = filterByCriteria(newsId, content);

        Page<CommentModel> listModels = commentRepository.findAll(specs, pageable);
        List<CommentDto> listDtos = CommentMapper.INSTANCE.toListDto(listModels.getContent());

        return new PageImpl<>(listDtos, pageable, listModels.getTotalElements());
    }





    private void isNewsIdExists(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new NoSuchEntityException(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), newsId));
        }
    }

    private Specification<CommentModel> filterByCriteria(Long newsId, String content) {
        Specification<CommentModel> resultSpecs = null;

        if (content != null) {
            resultSpecs = CommentSpecifications.hasContentLike(content.trim());
        }

        if (newsId != null) {
            Specification<CommentModel> newsIdSpec = CommentSpecifications.hasNewsIdLike(newsId);
            resultSpecs = resultSpecs == null ? newsIdSpec : resultSpecs.and(newsIdSpec);
        }

        return resultSpecs;
    }
}
