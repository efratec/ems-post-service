package br.com.algapost.api.common.mappers;

import br.com.algapost.api.common.GenerateSummary;
import br.com.algapost.api.model.PostInput;
import br.com.algapost.api.model.PostOutput;
import br.com.algapost.api.model.PostSummaryOutput;
import br.com.algapost.domain.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "wordCount", ignore = true)
    @Mapping(target = "calculatedValue", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    Post toEntity(PostInput input);

    @Mapping(target = "id", source = "id.value")
    PostOutput toOutput(Post post);

    List<PostOutput> toOutput(List<Post> posts);

    default Page<PostOutput> toOutput(Page<Post> posts) {
        return posts.map(this::toOutput);
    }

    default Page<PostSummaryOutput> toSummaryOutput(Page<Post> posts) {
        return posts.map(this::toSummaryOutput);
    }

    default PostSummaryOutput toSummaryOutput(Post post) {
        return PostSummaryOutput
                .builder()
                .id(post.getId().getValue())
                .title(post.getTitle())
                .author(post.getAuthor())
                .summary(GenerateSummary.generateSummary(post.getBody()))
                .build();
    }

}
