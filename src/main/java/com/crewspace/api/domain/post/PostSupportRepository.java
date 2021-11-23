package com.crewspace.api.domain.post;

import com.crewspace.api.domain.member.QMember;
import com.crewspace.api.domain.spaceMember.QSpaceMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PostSupportRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public PostSupportRepository(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Optional<CommunityPost> findCommunityPost(Long postId){
        QPost post = new QPost("post");
        QCommunityPost communityPost = post.as(QCommunityPost.class);

        QMember member = QMember.member;
        QPostCategory postCategory = QPostCategory.postCategory;
        QSpaceMember spaceMember = QSpaceMember.spaceMember;
        QPostImage postImage = QPostImage.postImage;

        return Optional.ofNullable(jpaQueryFactory
            .selectDistinct(communityPost)
            .from(communityPost)
            .join(communityPost.author, spaceMember).fetchJoin()
            .join(communityPost.postCategory, postCategory).fetchJoin()
            .leftJoin(communityPost.postImages, postImage).fetchJoin()
            .join(spaceMember.member, member).fetchJoin()
            .where(communityPost.id.eq(postId))
            .fetchOne());
    }

    public Optional<NoticePost> findNoticePost(Long postId){
        QPost post = new QPost("post");
        QNoticePost noticePost = post.as(QNoticePost.class);

        QMember member = QMember.member;
        QPostCategory postCategory = QPostCategory.postCategory;
        QSpaceMember spaceMember = QSpaceMember.spaceMember;
        QPostImage postImage = QPostImage.postImage;

        return Optional.ofNullable(jpaQueryFactory
            .selectDistinct(noticePost)
            .from(noticePost)
            .join(noticePost.author, spaceMember).fetchJoin()
            .join(noticePost.postCategory, postCategory).fetchJoin()
            .leftJoin(noticePost.postImages, postImage).fetchJoin()
            .join(spaceMember.member, member).fetchJoin()
            .where(noticePost.id.eq(postId))
            .fetchOne());
    }

}
