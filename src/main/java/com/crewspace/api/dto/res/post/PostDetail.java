package com.crewspace.api.dto.res.post;


import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.post.PostImage;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class PostDetail {
    private String categoryName;

    private String description;

    private Boolean isAuthor;
    private Boolean isSaved;

    private String writtenDate;

    private List<String> images;

    private void setWrittenDate(Post post){
        DayOfWeek dayOfWeek = post.getCreatedAt().getDayOfWeek();
        String day = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.writtenDate = String.format("%s (%s)", date, day);
    }

    private void setImages(List<PostImage> images){
        this.images = images.stream()
            .map(image -> image.getPath())
            .collect(Collectors.toList());
    }

    public PostDetail(Post post, Boolean isAuthor, Boolean isSaved){
        this.categoryName = post.getPostCategory().getName();
        this.description = post.getDescription();
        this.isAuthor = isAuthor;
        this.isSaved = isSaved;
        setWrittenDate(post);
        setImages(post.getPostImages());
    }

}
