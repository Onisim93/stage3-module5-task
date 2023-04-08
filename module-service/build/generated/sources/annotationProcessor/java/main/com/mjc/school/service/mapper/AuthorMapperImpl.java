package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-08T21:11:51+0400",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorModel toModel(AuthorDto authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        AuthorModel authorModel = new AuthorModel();

        authorModel.setId( authorDto.getId() );
        authorModel.setName( authorDto.getName() );

        return authorModel;
    }

    @Override
    public List<AuthorDto> toListDto(List<AuthorModel> authorModelList) {
        if ( authorModelList == null ) {
            return null;
        }

        List<AuthorDto> list = new ArrayList<AuthorDto>( authorModelList.size() );
        for ( AuthorModel authorModel : authorModelList ) {
            list.add( toDto( authorModel ) );
        }

        return list;
    }
}
