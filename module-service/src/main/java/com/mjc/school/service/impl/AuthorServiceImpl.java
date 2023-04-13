package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.aspect.annotation.EntityValidate;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.specifications.AuthorSpecifications;
import com.mjc.school.service.util.SortUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;
    private final NewsRepository newsRepository;

    public AuthorServiceImpl(AuthorRepository repository, NewsRepository newsRepository) {
        this.repository = repository;
        this.newsRepository = newsRepository;
    }


    @Override
    public AuthorDto getByNewsId(Long newsId) {
        return AuthorMapper.INSTANCE.toDto(newsRepository.findById(newsId).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getMessage(), newsId)))
        .getAuthor());
    }

    @Override
    public List<AuthorDto> readAll() {
        return AuthorMapper.INSTANCE.toListDto(repository.findAll());
    }

    @Override
    public AuthorDto readById(Long id) {
        return AuthorMapper.INSTANCE.toDto(repository.findById(id).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id))));
    }

    @Transactional
    @EntityValidate
    @Override
    public AuthorDto create(AuthorDto createRequest) {
        return AuthorMapper.INSTANCE.toDto(repository.save(AuthorMapper.INSTANCE.toModel(createRequest)));
    }

    @Transactional
    @EntityValidate
    @Override
    public AuthorDto update(AuthorDto updateRequest) {
        AuthorModel model = repository.findById(updateRequest.getId()).orElseThrow();
        if (updateRequest.getName() != null) {
            model.setName(updateRequest.getName());
        }
        repository.flush();
        return AuthorMapper.INSTANCE.toDto(model);
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        return repository.delete(id) != 0;
    }


    @Override
    public Page<AuthorDto> getAllByCriteria(int limit, int offset, String sortBy, Long newsId, String name, boolean withNews) {

        Pageable pageable = PageRequest.of(offset -1, limit, SortUtils.getSort(sortBy));
        Specification<AuthorModel> specs = filterByCriteria(newsId, name);

        Page<AuthorModel> listModels = repository.findAll(specs, pageable);
        List<AuthorDto> listDto;

        if (withNews) {
            listDto = AuthorMapper.INSTANCE.toListDtoWithNewsCount(listModels.getContent());
            listDto.sort(Comparator.comparingInt(AuthorDto::getNewsCount).reversed());
        }
        else {
            listDto = AuthorMapper.INSTANCE.toListDto(listModels.getContent());
        }

        return new PageImpl<>(listDto.stream().toList(), pageable, listModels.getTotalElements());
    }

    private Specification<AuthorModel> filterByCriteria(Long newsId, String name) {
        Specification<AuthorModel> resultSpecs = null;

        if (name != null) {
            resultSpecs = AuthorSpecifications.hasNameLike(name.trim());
        }

        if (newsId != null) {
            Specification<AuthorModel> newsIdSpec = AuthorSpecifications.hasNewsIdLike(newsId);
            resultSpecs = resultSpecs == null ? newsIdSpec : resultSpecs.and(newsIdSpec);
        }


        return resultSpecs;
    }
}
