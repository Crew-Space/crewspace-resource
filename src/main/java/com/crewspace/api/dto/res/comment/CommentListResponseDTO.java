package com.crewspace.api.dto.res.comment;

import com.crewspace.api.domain.comment.Comment;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentListResponseDTO {
    private int offset;
    private List<CommentList> comments;

    private CommentListResponseDTO(int offset, List<CommentList> comments) {
        this.offset = offset;
        this.comments = comments;
    }

    public static CommentListResponseDTO of(int offset, List<CommentList> comments){
        return new CommentListResponseDTO(offset, comments);
    }

    @Getter
    public static class CommentList {
        private Long authorId;
        private String authorName;
        private String authorCategoryName;
        private String authorImage;
        private String writtenDate;
        private String description;
        private Boolean isAuthor;

        private void setWrittenDate(Comment comment){
            DayOfWeek dayOfWeek = comment.getCreatedAt().getDayOfWeek();
            String day = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN);
            String date = comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
            this.writtenDate = String.format("%s (%s)", date, day);
        }

        public CommentList(Comment comment, Boolean isAuthor){
            this.authorId = comment.getCommentAuthor().getId();
            this.authorName = comment.getCommentAuthor().getName();
            this.authorCategoryName = comment.getCommentAuthor().getMemberCategory().getName();
            this.authorImage = comment.getCommentAuthor().getImage();
            this.description = comment.getDescription();
            this.isAuthor = isAuthor;
            setWrittenDate(comment);
        }
    }
}
