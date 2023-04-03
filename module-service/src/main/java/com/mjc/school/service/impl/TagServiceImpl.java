package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.TagService;
import com.mjc.school.service.aspect.annotation.EntityValidate;
import com.mjc.school.service.aspect.annotation.IdValidate;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.mapper.TagMapper;
import com.mjc.school.service.specifications.AuthorSpecifications;
import com.mjc.school.service.specifications.TagSpecifications;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<TagDto> readAll() {
        return TagMapper.INSTANCE.toListDto(repository.findAll());
    }

    @Override
    public TagDto readById(Long id) {
        return TagMapper.INSTANCE.toDto(repository.findById(id).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getMessage(), id))));
    }

    @Override
    @Transactional
    @EntityValidate
    public TagDto create(TagDto createRequest) {
        return TagMapper.INSTANCE.toDto(repository.saveAndFlush(TagMapper.INSTANCE.toModel(createRequest)));
    }

    @Override
    @Transactional
    @EntityValidate
    public TagDto update(TagDto updateRequest) {
        return TagMapper.INSTANCE.toDto(repository.saveAndFlush(TagMapper.INSTANCE.toModel(updateRequest)));
    }

    @Override
    @Transactional
    public TagDto patch(TagDto patchRequest) {
        TagModel model = repository.findById(patchRequest.getId()).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getMessage(), patchRequest.getId())));

        if (patchRequest.getName() != null) {
            model.setName(patchRequest.getName());
        }

        return TagMapper.INSTANCE.toDto(model);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return repository.delete(id) != 0;
    }

    @Override
    public Page<TagDto> getAllByCriteria(int limit, int offset, String sortBy, String newsId, String name) {
        Sort sort = Sort.by(sortBy);

        Pageable pageable = PageRequest.of(offset -1, limit, sort);
        Specification<TagModel> specs = filterByCriteria(newsId, name);

        Page<TagModel> listModels = repository.findAll(specs, pageable);
        List<TagDto> listDtos = TagMapper.INSTANCE.toListDto(listModels.getContent());


        return new PageImpl<>(listDtos, pageable, listModels.getTotalElements());
    }

    @Override
    public List<TagDto> getAllByNewsId(Long newsId) {
        return TagMapper.INSTANCE.toListDto(repository.findAllByNewsId(newsId));
    }

    @IdValidate
    private Specification<TagModel> filterByCriteria(String newsId, String name) {
        Specification<TagModel> resultSpecs = null;


        if (name != null) {
            resultSpecs = TagSpecifications.hasNameLike(name);
        }

        if (newsId != null) {
            Specification<TagModel> newsIdSpec = TagSpecifications.hasNewsIdLike(Long.parseLong(newsId));
            resultSpecs = resultSpecs == null ? newsIdSpec : resultSpecs.and(newsIdSpec);
        }

        return resultSpecs;
    }
}
