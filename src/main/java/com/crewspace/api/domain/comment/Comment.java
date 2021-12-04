package com.crewspace.api.domain.comment;

import com.crewspace.api.domain.BaseTimeEntity;
import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_member_id")
    private SpaceMember commentAuthor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(length = 300)
    private String description;

    private Comment(SpaceMember commentAuthor, Post post, String description) {
        this.commentAuthor = commentAuthor;
        this.post = post;
        this.description = description;
    }

    public static Comment of(SpaceMember commentAuthor, Post post, String description){
        return new Comment(commentAuthor, post, description);
    }
}
