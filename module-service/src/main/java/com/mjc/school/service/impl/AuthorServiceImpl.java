package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.aspect.annotation.EntityValidate;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.exception.NoSuchEntityException;
import com.mjc.school.service.exception.ServiceErrorCode;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.specifications.AuthorSpecifications;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }


    @Override
    public AuthorDto getByNewsId(Long newsId) {
        return null;
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
        return AuthorMapper.INSTANCE.toDto(repository.saveAndFlush(AuthorMapper.INSTANCE.toModel(createRequest)));
    }

    @Transactional
    @EntityValidate
    @Override
    public AuthorDto update(AuthorDto updateRequest) {
        return AuthorMapper.INSTANCE.toDto(repository.saveAndFlush(AuthorMapper.INSTANCE.toModel(updateRequest)));
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        return repository.delete(id) != 0;
    }

    @Override
    public Page<AuthorDto> getAllByCriteria(int page, int limit, String sortBy, List<String> filterParams) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page-1, limit, sort);
        Specification<AuthorModel> specs = filterByCriteria(filterParams);

        Page<AuthorModel> listModels = specs == null ? repository.findAll(pageable) : repository.findAll(specs, pageable);
        List<AuthorDto> listDto = AuthorMapper.INSTANCE.toListDto(listModels.getContent());

        return new PageImpl<>(listDto, pageable, listModels.getTotalElements());
    }

    private Specification<AuthorModel> filterByCriteria(List<String> parameters) {
        Specification<AuthorModel> resultSpecs = null;

        for (String parameter : parameters) {
            String[] param = parameter.split("=");
            Specification<AuthorModel> spec = switch (param[0]) {
                case "name" -> AuthorSpecifications.hasNameLike(param[1].trim());
                case "newsId" -> AuthorSpecifications.hasNewsIdLike(Long.parseLong(param[1].trim()));
                default ->  null;
            };

            resultSpecs = resultSpecs == null ? spec : resultSpecs.and(spec);
        }

        return resultSpecs;
    }
}
