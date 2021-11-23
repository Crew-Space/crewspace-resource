package com.crewspace.api.dto.req.post;

import com.crewspace.api.domain.post.NoticePost;
import com.crewspace.api.domain.post.NoticeTarget;
import com.crewspace.api.domain.post.PostCategory;
import com.crewspace.api.domain.post.PostImage;
import com.crewspace.api.domain.spaceMember.MemberCategory;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WriteNoticeRequestDTO {

    private Long spaceId;
    private String memberEmail;
    private Long postCategoryId;

    private List<Long> targets;

    private List<String> images;
    private String title;
    private String description;

    private Boolean isReserved;
    private LocalDateTime reservedTime;

    @Builder
    public WriteNoticeRequestDTO(Long spaceId, String memberEmail, Long postCategoryId,
        List<Long> targets, List<String> images, String title, String description,
        Boolean isReserved, LocalDateTime reservedTime) {
        this.spaceId = spaceId;
        this.memberEmail = memberEmail;
        this.postCategoryId = postCategoryId;
        this.targets = targets;
        this.images = images;
        this.title = title;
        this.description = description;
        this.isReserved = isReserved;
        this.reservedTime = reservedTime;
    }


    public NoticePost toNoticePost(SpaceMember spaceMember, PostCategory postCategory){
        return NoticePost.builder()
            .author(spaceMember)
            .postCategory(postCategory)
            .title(title)
            .description(description)
            .isShowed(isReserved)
            .reservedTime(reservedTime)
            .build();
    }

    public List<PostImage> toPostImages(NoticePost noticePost){
        List<PostImage> postImages = new ArrayList<>();
        for(int i = 0 ; i < images.size() ; i++){
            if(i == 0) postImages.add(PostImage.of(noticePost, true, images.get(i)));
            else postImages.add(PostImage.of(noticePost, false, images.get(i)));
        }
        return postImages;
    }

    public List<NoticeTarget> toNoticeTarget(NoticePost noticePost, List<MemberCategory> memberCategory){
        return memberCategory.stream()
            .map(category -> NoticeTarget.of(category, noticePost))
            .collect(Collectors.toList());
    }
}

