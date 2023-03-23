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
import org.springframework.data.domain.*;
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
        isNewsIdExists(updateRequest.getNewsId());
        return CommentMapper.INSTANCE.toDto(commentRepository.saveAndFlush(CommentMapper.INSTANCE.toModel(updateRequest)));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return commentRepository.delete(id) != 0;
    }

    @Override
    public Page<CommentDto> getAllByCriteria(int page, int limit, String sortBy, List<String> filterParams) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page-1, limit, sort);
        Specification<CommentModel> specs = filterByCriteria(filterParams);

        Page<CommentModel> listModels = commentRepository.findAll(specs, pageable);
        List<CommentDto> listDtos = CommentMapper.INSTANCE.toListDto(listModels.getContent());

        return new PageImpl<>(listDtos, pageable, listModels.getTotalElements());
    }

    @Override
    public List<CommentDto> getAllByNewsId(Long newsId) {
        return CommentMapper.INSTANCE.toListDto(commentRepository.findAllByNewsId(newsId));
    }

    private void isNewsIdExists(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new NoSuchEntityException(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), newsId));
        }
    }

    private Specification<CommentModel> filterByCriteria(List<String> parameters) {
        Specification<CommentModel> resultSpecs = null;

        for (String parameter : parameters) {
            String[] param = parameter.split("=");
            Specification<CommentModel> spec = switch (param[0]) {
                case "content" -> CommentSpecifications.hasContentLike(param[1].trim());
                case "newsId" -> CommentSpecifications.hasNewsIdLike(Long.parseLong(param[1].trim()));
                default ->  null;
            };

            resultSpecs = resultSpecs == null ? spec : resultSpecs.and(spec);
        }

        return resultSpecs;
    }
}
