package com.crewspace.api.dto.res.post;

import com.crewspace.api.domain.post.NoticePost;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoticePostListResponseDTO  {

    private int offset;
    private String type;
    private List<NoticePostList> posts;

    private NoticePostListResponseDTO(int offset, String type,
        List<NoticePostList> posts) {
        this.offset = offset;
        this.type = type;
        this.posts = posts;
    }

    public static NoticePostListResponseDTO of(int offset, String type, List<NoticePostList> posts){
        return new NoticePostListResponseDTO(offset, type, posts);
    }

    @Getter
    public static class NoticePostList extends PostList{
        private String title;
        private Boolean isSaved;
        private Boolean isRead;

        public NoticePostList(NoticePost post, Long isSaved) {
            super(post);
            this.title = post.getTitle();
            this.isSaved = !Objects.isNull(isSaved);
            this.isRead = false;
        }
        public NoticePostList(NoticePost post, Long isSaved, Long isRead) {
            super(post);
            this.title = post.getTitle();
            this.isSaved = !Objects.isNull(isSaved);
            this.isRead = !Objects.isNull(isRead);
        }
    }

}
