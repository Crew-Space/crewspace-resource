package com.crewspace.api.domain.post;

import com.crewspace.api.domain.member.QMember;
import com.crewspace.api.domain.memberPost.QFixedPost;
import com.crewspace.api.domain.memberPost.QMemberPost;
import com.crewspace.api.domain.memberPost.QReadPost;
import com.crewspace.api.domain.memberPost.QSavedPost;
import com.crewspace.api.domain.memberPost.SavedPost;
import com.crewspace.api.domain.spaceMember.QMemberCategory;
import com.crewspace.api.domain.spaceMember.QSpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.dto.res.post.CommunityPostListResponseDTO.CommunityPostList;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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

    // 최신순
    public List<NoticePost> findFixedPost(SpaceMember spaceMember){
        QPost post = new QPost("post");
        QNoticePost noticePost = post.as(QNoticePost.class);

        QMemberPost memberPost = new QMemberPost("memberPost");
        QFixedPost fixedPost = memberPost.as(QFixedPost.class);
        QPostCategory postCategory = QPostCategory.postCategory;

        return jpaQueryFactory
            .selectDistinct(noticePost)
            .from(fixedPost)
            .join(noticePost).on(fixedPost.post.id.eq(noticePost.id))
            .join(post.postCategory, postCategory).fetchJoin()
            .where(fixedPost.member.eq(spaceMember))
            .orderBy(post.createdAt.desc())
            .limit(10)
            .fetch();
    }

    // 최신순
    public List<Tuple> findNewPost(SpaceMember spaceMember){
        QPost post = new QPost("post");
        QNoticePost noticePost = post.as(QNoticePost.class);

        QMemberPost memberPost = new QMemberPost("memberPost");
        QSavedPost savedPost = memberPost.as(QSavedPost.class);
        QReadPost readPost = memberPost.as(QReadPost.class);

        QPostCategory postCategory = QPostCategory.postCategory;

        return jpaQueryFactory
            .select(noticePost, readPost.post.id, savedPost.post.id)
            .from(noticePost)
            .join(post.postCategory, postCategory).fetchJoin()
            .leftJoin(savedPost)
            .on(noticePost.id.eq(savedPost.post.id))
            .on(savedPost.member.eq(spaceMember))
            .leftJoin(readPost)
            .on(noticePost.id.eq(readPost.post.id))
            .on(readPost.member.eq(spaceMember))
            .orderBy(post.createdAt.desc())
            .limit(10)
            .fetch();
    }

    public List<CommunityPostList> allCommunityList(SpaceMember reader, Pageable pageable, Long postCategoryId){
        QPost post = new QPost("post");
        QCommunityPost communityPost = post.as(QCommunityPost.class);

        QMember member = QMember.member;
        QPostCategory postCategory = QPostCategory.postCategory;
        QSpaceMember spaceMember = QSpaceMember.spaceMember;

        QMemberCategory memberCategory = QMemberCategory.memberCategory;

        QMemberPost memberPost = new QMemberPost("memberPost");
        QSavedPost savedPost = memberPost.as(QSavedPost.class);

        if(postCategoryId.equals(Long.valueOf(-1))){
            return jpaQueryFactory
                .select(Projections.constructor(CommunityPostList.class, communityPost, savedPost.post.id))
                .from(communityPost)
                .join(communityPost.author, spaceMember).fetchJoin()
                .join(communityPost.postCategory, postCategory).fetchJoin()
                .join(spaceMember.memberCategory, memberCategory).fetchJoin()
                .join(spaceMember.member, member).fetchJoin()
                .leftJoin(savedPost).on(communityPost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .orderBy(communityPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }else{
            return jpaQueryFactory
                .select(Projections.constructor(CommunityPostList.class, communityPost, savedPost.post.id))
                .from(communityPost)
                .join(communityPost.author, spaceMember).fetchJoin()
                .join(communityPost.postCategory, postCategory).fetchJoin()
                .join(spaceMember.memberCategory, memberCategory).fetchJoin()
                .join(spaceMember.member, member).fetchJoin()
                .leftJoin(savedPost).on(communityPost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .where(communityPost.postCategory.id.eq(postCategoryId))
                .orderBy(communityPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }

    }

    public List<CommunityPostList> saveCommunityList(SpaceMember reader, Pageable pageable, Long postCategoryId){
        QPost post = new QPost("post");
        QCommunityPost communityPost = post.as(QCommunityPost.class);

        QMember member = QMember.member;
        QPostCategory postCategory = QPostCategory.postCategory;
        QSpaceMember spaceMember = QSpaceMember.spaceMember;

        QMemberCategory memberCategory = QMemberCategory.memberCategory;

        QMemberPost memberPost = new QMemberPost("memberPost");
        QSavedPost savedPost = memberPost.as(QSavedPost.class);

        if(postCategoryId.equals(Long.valueOf(-1))){
            return jpaQueryFactory
                .select(Projections.constructor(CommunityPostList.class, communityPost, savedPost.post.id))
                .from(communityPost)
                .join(communityPost.author, spaceMember).fetchJoin()
                .join(communityPost.postCategory, postCategory).fetchJoin()
                .join(spaceMember.memberCategory, memberCategory).fetchJoin()
                .join(spaceMember.member, member).fetchJoin()
                .join(savedPost).on(communityPost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .orderBy(communityPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }else{
            return jpaQueryFactory
                .select(Projections.constructor(CommunityPostList.class, communityPost, savedPost.post.id))
                .from(communityPost)
                .join(communityPost.author, spaceMember).fetchJoin()
                .join(communityPost.postCategory, postCategory).fetchJoin()
                .join(spaceMember.memberCategory, memberCategory).fetchJoin()
                .join(spaceMember.member, member).fetchJoin()
                .join(savedPost).on(communityPost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .where(communityPost.postCategory.id.eq(postCategoryId))
                .orderBy(communityPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }

    }

}
