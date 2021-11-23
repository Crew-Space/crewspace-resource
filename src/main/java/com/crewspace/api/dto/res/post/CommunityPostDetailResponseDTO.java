package com.crewspace.api.dto.res.post;

import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.post.PostImage;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityPostDetailResponseDTO extends PostDetail {

    private String author;
    private Long authorId;
    private String authorCategoryName;
    private String authorImage;

    @Builder
    public CommunityPostDetailResponseDTO(Post post, Boolean isAuthor, Boolean isSaved) {
        super(post, isAuthor, isSaved);
        this.author = post.getAuthor().getName();
        this.authorId = post.getAuthor().getId();
        this.authorImage = post.getAuthor().getImage();
        this.authorCategoryName = post.getPostCategory().getName();
    }
}
