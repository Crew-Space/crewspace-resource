package com.crewspace.api.dto.res.post;

import com.crewspace.api.domain.post.NoticePost;
import com.crewspace.api.domain.post.NoticeTarget;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class NoticePostDetailResponseDTO  extends PostDetail{

    private String title;
    private Boolean isFixed;
    private List<target> targets;

    @Getter
    @AllArgsConstructor
    public static class target {
        private Long targetId;
        private String targetName;
    }

    @Builder
    public NoticePostDetailResponseDTO(NoticePost post, List<NoticeTarget> targets,Boolean isAuthor, Boolean isSaved, Boolean isFixed) {
        super(post, isAuthor, isSaved);
        this.title = post.getTitle();
        this.isFixed = isFixed;
        this.targets = targets.stream()
            .map(t -> new target(t.getTarget().getId(), t.getTarget().getName()))
            .collect(Collectors.toList());
    }
}
