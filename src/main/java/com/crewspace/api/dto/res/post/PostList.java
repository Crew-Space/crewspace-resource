package com.crewspace.api.dto.res.post;

import com.crewspace.api.domain.post.NoticePost;
import com.crewspace.api.domain.post.Post;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class PostList {

    private Long postId;
    private String categoryName;
    private String description;
    private String writtenDate;
    private String image;

    public PostList(Post post){
        this.postId = post.getId();
        this.categoryName = post.getPostCategory().getName();
        this.description = post.getDescription();
        this.writtenDate = setWrittenDate(post);
        this.image = post.getPostImages().size() > 0 ? post.getPostImages().get(0).getPath() : "";
    }
    private static String setWrittenDate(Post post){
        DayOfWeek dayOfWeek = post.getCreatedAt().getDayOfWeek();
        String day = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return String.format("%s (%s)", date, day);
    }
}
