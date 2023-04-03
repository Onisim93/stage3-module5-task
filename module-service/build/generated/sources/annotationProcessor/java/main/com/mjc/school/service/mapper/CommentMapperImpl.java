package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.CommentModel;
import com.mjc.school.service.dto.CommentDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-01T00:05:09+0400",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentModel toModel(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        CommentModel commentModel = new CommentModel();

        commentModel.setId( commentDto.getId() );
        commentModel.setContent( commentDto.getContent() );

        return commentModel;
    }

    @Override
    public List<CommentDto> toListDto(List<CommentModel> commentModelList) {
        if ( commentModelList == null ) {
            return null;
        }

        List<CommentDto> list = new ArrayList<CommentDto>( commentModelList.size() );
        for ( CommentModel commentModel : commentModelList ) {
            list.add( toDto( commentModel ) );
        }

        return list;
    }
}
