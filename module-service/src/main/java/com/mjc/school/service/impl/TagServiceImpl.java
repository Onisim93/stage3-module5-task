package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.TagService;
import com.mjc.school.service.aspect.annotation.EntityValidate;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.mapper.TagMapper;
import com.mjc.school.service.specifications.TagSpecifications;
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
        TagModel model = repository.findById(updateRequest.getId()).orElseThrow(
                ()->new NoSuchEntityException(String.format(ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getMessage(), updateRequest.getId())));

        if (updateRequest.getName() != null) {
            model.setName(updateRequest.getName());
        }

        repository.flush();
        return TagMapper.INSTANCE.toDto(model);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return repository.delete(id) != 0;
    }

    @Override
    public Page<TagDto> getAllByCriteria(int limit, int offset, String sortBy, Long newsId, String name) {
        Pageable pageable = PageRequest.of(offset -1, limit, SortUtils.getSort(sortBy));
        Specification<TagModel> specs = filterByCriteria(newsId, name);

        Page<TagModel> listModels = repository.findAll(specs, pageable);
        List<TagDto> listDtos = TagMapper.INSTANCE.toListDto(listModels.getContent());


        return new PageImpl<>(listDtos, pageable, listModels.getTotalElements());
    }

    private Specification<TagModel> filterByCriteria(Long newsId, String name) {
        Specification<TagModel> resultSpecs = null;


        if (name != null) {
            resultSpecs = TagSpecifications.hasNameLike(name.trim());
        }

        if (newsId != null) {
            Specification<TagModel> newsIdSpec = TagSpecifications.hasNewsIdLike(newsId);
            resultSpecs = resultSpecs == null ? newsIdSpec : resultSpecs.and(newsIdSpec);
        }

        return resultSpecs;
    }
}
