package com.crewspace.api.dto.res.post;

import com.crewspace.api.domain.post.CommunityPost;
import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityPostListResponseDTO {

    private int offset;
    private String type;
    private List<CommunityPostList> posts;

    private CommunityPostListResponseDTO(int offset, String type, List<CommunityPostList> posts) {
        this.offset = offset;
        this.type = type;
        this.posts = posts;
    }

    public static CommunityPostListResponseDTO of(int offset, String type, List<CommunityPostList> posts){
        return new CommunityPostListResponseDTO(offset, type, posts);
    }

    @Getter
    public static class CommunityPostList extends PostList {

        private Long authorId;
        private String authorName;
        private String authorImage;
        private String authorCategoryName;
        private Boolean isSaved;

        @Builder
        public CommunityPostList(CommunityPost post, Long isSaved) {
            super(post);
            this.authorId = post.getAuthor().getId();
            this.authorName = post.getAuthor().getName();
            this.authorImage = post.getAuthor().getImage();
            this.authorCategoryName = post.getAuthor().getMemberCategory().getName();
            this.isSaved = !Objects.isNull(isSaved);
        }
    }


}
