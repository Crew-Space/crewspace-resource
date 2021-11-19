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
public class PostImage extends BaseTimeEntity{
    @Id @GeneratedValue
    @Column(name = "post_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Boolean isThumbnail;
    private String path;

    // 연관관계 메서드
    public void setPost(Post post){
        this.post = post;
        post.getPostImages().add(this);
    }

    // 생성 메서드
    @Builder
    public PostImage(Post post, Boolean isThumbnail, String path) {
        this.isThumbnail = isThumbnail;
        this.path = path;
        this.setPost(post);
    }
}
