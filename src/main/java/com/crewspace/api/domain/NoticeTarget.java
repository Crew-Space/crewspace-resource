package com.crewspace.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class NoticeTarget {

    @Id @GeneratedValue
    @Column(name = "notice_target_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_category_id")
    private MemberCategory target;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private NoticePost noticePost;

    // 연관관계 메서드
    public void setNoticePost(NoticePost post){
        this.noticePost = post;
        post.getTargets().add(this);
    }

    // 생성 메서드
    @Builder
    public NoticeTarget(MemberCategory target, NoticePost noticePost) {
        this.target = target;
        this.setNoticePost(noticePost);
    }
}
