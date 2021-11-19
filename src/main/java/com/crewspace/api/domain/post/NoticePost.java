package com.crewspace.api.domain.post;

import com.crewspace.api.domain.spaceMember.SpaceMember;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class NoticePost extends Post {

    private String title;
    private Boolean isShowed;
    private LocalDateTime reservedTime;

    @OneToMany(mappedBy = "noticePost", cascade = CascadeType.ALL)
    private List<NoticeTarget> targets = new ArrayList<>();

    @Builder
    public NoticePost(SpaceMember author, PostCategory postCategory, String description, String title, Boolean isShowed, LocalDateTime reservedTime) {
        super(author, postCategory, description);
        this.title = title;
        this.isShowed = isShowed;
        this.reservedTime = reservedTime;
    }
}
