package com.crewspace.api.dto.res.space;

import com.crewspace.api.domain.post.NoticePost;
import com.crewspace.api.domain.space.Space;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SpaceMainResponseDTO {

    private Boolean isAdmin;
    private String spaceName;
    private String spaceImage;
    private String bannerImage;

    private List<FixedNotice> fixedNotices;
    private List<NewNotice> newNotices;


    @Builder
    public SpaceMainResponseDTO(SpaceMember spaceMember,
        List<NoticePost> fixedNotices, List<NewNotice> newNotices){
        this.isAdmin = spaceMember.getIsAdmin();
        this.spaceName = spaceMember.getSpace().getName();
        this.spaceImage = spaceMember.getSpace().getImage();
        this.bannerImage = spaceMember.getSpace().getBannerImage();

        this.fixedNotices = fixedNotices.stream()
            .map(notice-> new FixedNotice(notice))
            .collect(Collectors.toList());

        this.newNotices = newNotices;

    }

    @Getter
    public static class FixedNotice {
        private String categoryName;
        private String title;
        private String writtenDate;

        @Builder
        public FixedNotice(NoticePost post){
            this.categoryName = post.getPostCategory().getName();
            this.title = post.getTitle();
            this.writtenDate = setWrittenDate(post);
        }
    }

    @Getter
    @JsonInclude(Include.NON_NULL)
    public static class NewNotice {
        private Long postId;
        private String categoryName;
        private String title;
        private String description;
        private String writtenDate;

        private String image;

        private Boolean isRead;
        private Boolean isSaved;

        @Builder
        public NewNotice(NoticePost post, Long isRead, Long isSaved){
            this.postId = post.getId();
            this.categoryName = post.getPostCategory().getName();
            this.title = post.getTitle();
            this.description = post.getDescription();
            this.writtenDate = setWrittenDate(post);
            this.image = post.getPostImages().size() > 0 ? post.getPostImages().get(0).getPath() : "";
            this.isRead = !Objects.isNull(isRead);
            this.isSaved = !Objects.isNull(isSaved);
        }
    }

    private static String setWrittenDate(NoticePost post){
        DayOfWeek dayOfWeek = post.getCreatedAt().getDayOfWeek();
        String day = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN);
        String date = post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        return String.format("%s (%s)", date, day);
    }
}
