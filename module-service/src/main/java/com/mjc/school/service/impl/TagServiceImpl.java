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
import org.springframework.data.domain.*;
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
        return TagMapper.INSTANCE.toDto(repository.saveAndFlush(TagMapper.INSTANCE.toModel(updateRequest)));
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        return repository.delete(id) != 0;
    }

    @Override
    public Page<TagDto> getAllByCriteria(int page, int limit, String sortBy, List<String> filterParams) {
        Sort sort = Sort.by(sortBy);

        Pageable pageable = PageRequest.of(page-1, limit, sort);
        Specification<TagModel> specs = filterByCriteria(filterParams);

        Page<TagModel> listModels = repository.findAll(specs, pageable);
        List<TagDto> listDtos = TagMapper.INSTANCE.toListDto(listModels.getContent());


        return new PageImpl<>(listDtos, pageable, listModels.getTotalElements());
    }

    @Override
    public List<TagDto> getAllByNewsId(Long newsId) {
        return TagMapper.INSTANCE.toListDto(repository.findAllByNewsId(newsId));
    }

    private Specification<TagModel> filterByCriteria(List<String> parameters) {
        Specification<TagModel> resultSpecs = null;

        for (String parameter : parameters) {
            String[] param = parameter.split("=");
            Specification<TagModel> spec = switch (param[0]) {
                case "name" -> TagSpecifications.hasNameLike(param[1].trim());
                case "newsId" -> TagSpecifications.hasNewsIdLike(Long.parseLong(param[1].trim()));
                default ->  null;
            };

            resultSpecs = resultSpecs == null ? spec : resultSpecs.and(spec);

        }
        return resultSpecs;
    }
}
