package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.dto.TagDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-14T15:19:37+0400",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagDto toDto(TagModel tagModel) {
        if ( tagModel == null ) {
            return null;
        }

        TagDto tagDto = new TagDto();

        tagDto.setId( tagModel.getId() );
        tagDto.setName( tagModel.getName() );
        tagDto.setCreated( tagModel.getCreated() );
        tagDto.setModified( tagModel.getModified() );

        return tagDto;
    }

    @Override
    public TagModel toModel(TagDto tagDto) {
        if ( tagDto == null ) {
            return null;
        }

        TagModel tagModel = new TagModel();

        tagModel.setId( tagDto.getId() );
        tagModel.setName( tagDto.getName() );

        return tagModel;
    }
}
