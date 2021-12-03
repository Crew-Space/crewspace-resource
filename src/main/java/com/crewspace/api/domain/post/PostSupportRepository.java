package com.crewspace.api.domain.post;

import com.crewspace.api.domain.member.QMember;
import com.crewspace.api.domain.memberPost.QFixedPost;
import com.crewspace.api.domain.memberPost.QMemberPost;
import com.crewspace.api.domain.memberPost.QReadPost;
import com.crewspace.api.domain.memberPost.QSavedPost;
import com.crewspace.api.domain.spaceMember.MemberCategory;
import com.crewspace.api.domain.spaceMember.QMemberCategory;
import com.crewspace.api.domain.spaceMember.QSpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.dto.res.post.CommunityPostListResponseDTO.CommunityPostList;
import com.crewspace.api.dto.res.post.NoticePostListResponseDTO.NoticePostList;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class PostSupportRepository extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    QPost post = new QPost("post");
    QNoticePost noticePost = post.as(QNoticePost.class);
    QCommunityPost communityPost = post.as(QCommunityPost.class);

    QMember member = QMember.member;
    QPostCategory postCategory = QPostCategory.postCategory;
    QSpaceMember spaceMember = QSpaceMember.spaceMember;
    QPostImage postImage = QPostImage.postImage;
    QMemberCategory memberCategory = QMemberCategory.memberCategory;

    QMemberPost memberPost = new QMemberPost("memberPost");
    QMemberPost memberPost1 = new QMemberPost("memberPost1");
    QMemberPost memberPost2 = new QMemberPost("memberPost2");

    QFixedPost fixedPost = memberPost.as(QFixedPost.class);
    QSavedPost savedPost = memberPost1.as(QSavedPost.class);
    QReadPost readPost = memberPost2.as(QReadPost.class);

    QNoticeTarget noticeTarget = QNoticeTarget.noticeTarget;


    public PostSupportRepository(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Optional<CommunityPost> findCommunityPost(Long postId){
        return Optional.ofNullable(jpaQueryFactory
            .selectDistinct(communityPost)
            .from(communityPost)
            .join(communityPost.author, spaceMember).fetchJoin()
            .join(communityPost.postCategory, postCategory).fetchJoin()
            .leftJoin(communityPost.postImages, postImage).fetchJoin()
            .join(spaceMember.memberCategory, memberCategory).fetchJoin()
            .join(spaceMember.member, member).fetchJoin()
            .where(communityPost.id.eq(postId))
            .fetchOne());
    }

    public Optional<NoticePost> findNoticePost(Long postId){
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
            .where(postCategory.space.eq(spaceMember.getSpace()))
            .orderBy(post.createdAt.desc())
            .limit(10)
            .fetch();
    }

    public List<CommunityPostList> allCommunityList(SpaceMember reader, Pageable pageable, Long postCategoryId){
        if(postCategoryId.equals(Long.valueOf(-1))){
            return jpaQueryFactory
                .select(Projections.constructor(CommunityPostList.class, communityPost, savedPost.post.id))
                .from(communityPost)
                .join(communityPost.author, spaceMember).fetchJoin()
                .join(communityPost.postCategory, postCategory).fetchJoin()
                .join(spaceMember.memberCategory, memberCategory).fetchJoin()
                .join(spaceMember.member, member).fetchJoin()
                .leftJoin(savedPost).on(communityPost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .where(postCategory.space.eq(reader.getSpace()))
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
                .where(communityPost.postCategory.id.eq(postCategoryId).and(postCategory.space.eq(reader.getSpace())))
                .orderBy(communityPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }

    }

    public List<CommunityPostList> saveCommunityList(SpaceMember reader, Pageable pageable, Long postCategoryId){
        if(postCategoryId.equals(Long.valueOf(-1))){
            return jpaQueryFactory
                .select(Projections.constructor(CommunityPostList.class, communityPost, savedPost.post.id))
                .from(communityPost)
                .join(communityPost.author, spaceMember).fetchJoin()
                .join(communityPost.postCategory, postCategory).fetchJoin()
                .join(spaceMember.memberCategory, memberCategory).fetchJoin()
                .join(spaceMember.member, member).fetchJoin()
                .join(savedPost).on(communityPost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .where(postCategory.space.eq(reader.getSpace()))
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
                .where(communityPost.postCategory.id.eq(postCategoryId).and(postCategory.space.eq(reader.getSpace())))
                .orderBy(communityPost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }

    }

    // notice post 의 postcategory의 space와 spacemember space가 같아야함
    public List<NoticePostList> allNoticeList(SpaceMember reader, Pageable pageable, Long postCategoryId){

        if(postCategoryId.equals(Long.valueOf(-2))){
            return jpaQueryFactory
                .select(Projections.constructor(NoticePostList.class, noticePost, savedPost.post.id, readPost.post.id))
                .from(noticePost)
                .join(noticePost.author, spaceMember).fetchJoin()
                .join(noticePost.postCategory, postCategory).fetchJoin()
                .leftJoin(readPost).on(noticePost.id.eq(readPost.post.id)).on(readPost.member.eq(reader))
                .leftJoin(savedPost).on(noticePost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .where(postCategory.space.eq(reader.getSpace()))
                .orderBy(noticePost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }else if(postCategoryId.equals(Long.valueOf(-1))){
            return jpaQueryFactory
                .select(Projections.constructor(NoticePostList.class, noticePost, savedPost.post.id, readPost.post.id))
                .from(noticePost)
                .join(noticePost.author, spaceMember).fetchJoin()
                .join(noticePost.postCategory, postCategory).fetchJoin()
                .join(noticePost.targets, noticeTarget).fetchJoin()
                .leftJoin(readPost).on(noticePost.id.eq(readPost.post.id)).on(readPost.member.eq(reader))
                .leftJoin(savedPost).on(noticePost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .where(postCategory.space.eq(reader.getSpace()))
                .where(noticeTarget.target.eq(reader.getMemberCategory()))
                .orderBy(noticePost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }else{
            return jpaQueryFactory
                .select(Projections.constructor(NoticePostList.class, noticePost, savedPost.post.id, readPost.post.id))
                .from(noticePost)
                .join(noticePost.author, spaceMember).fetchJoin()
                .join(noticePost.postCategory, postCategory).fetchJoin()
                .leftJoin(savedPost).on(noticePost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .leftJoin(readPost).on(noticePost.id.eq(readPost.post.id)).on(readPost.member.eq(reader))
                .where(noticePost.postCategory.id.eq(postCategoryId).and(postCategory.space.eq(reader.getSpace())))
                .orderBy(noticePost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }

    }

    public List<NoticePostList> saveNoticeList(SpaceMember reader, Pageable pageable, Long postCategoryId){

        if(postCategoryId.equals(Long.valueOf(-2))){
            return jpaQueryFactory
                .select(Projections.constructor(NoticePostList.class, noticePost, savedPost.post.id, readPost.post.id))
                .from(noticePost)
                .join(noticePost.author, spaceMember).fetchJoin()
                .join(noticePost.postCategory, postCategory).fetchJoin()
                .join(savedPost).on(noticePost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .leftJoin(readPost).on(noticePost.id.eq(readPost.post.id)).on(readPost.member.eq(reader))
                .where(postCategory.space.eq(reader.getSpace()))
                .orderBy(noticePost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }else if(postCategoryId.equals(Long.valueOf(-1))){
            return jpaQueryFactory
                .select(Projections.constructor(NoticePostList.class, noticePost, savedPost.post.id, readPost.post.id))
                .from(noticePost)
                .join(noticePost.author, spaceMember).fetchJoin()
                .join(noticePost.postCategory, postCategory).fetchJoin()
                .join(noticePost.targets, noticeTarget).fetchJoin()
                .join(savedPost).on(noticePost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .leftJoin(readPost).on(noticePost.id.eq(readPost.post.id)).on(readPost.member.eq(reader))
                .where(postCategory.space.eq(reader.getSpace()))
                .where(noticeTarget.target.eq(reader.getMemberCategory()))
                .orderBy(noticePost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }else{
            return jpaQueryFactory
                .select(Projections.constructor(NoticePostList.class, noticePost, savedPost.post.id, readPost.post.id))
                .from(noticePost)
                .join(noticePost.author, spaceMember).fetchJoin()
                .join(noticePost.postCategory, postCategory).fetchJoin()
                .join(savedPost).on(noticePost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .leftJoin(readPost).on(noticePost.id.eq(readPost.post.id)).on(readPost.member.eq(reader))
                .where(noticePost.postCategory.id.eq(postCategoryId).and(postCategory.space.eq(reader.getSpace())))
                .orderBy(noticePost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }

    }

    public List<NoticePostList> notReadNoticeList(SpaceMember reader, Pageable pageable, Long postCategoryId){
        if(postCategoryId.equals(Long.valueOf(-2))){
            return jpaQueryFactory
                .select(Projections.constructor(NoticePostList.class, noticePost, savedPost.post.id))
                .from(noticePost)
                .join(noticePost.author, spaceMember).fetchJoin()
                .join(noticePost.postCategory, postCategory).fetchJoin()
                .leftJoin(savedPost).on(noticePost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .where(noticePost.id.notIn(JPAExpressions.select(readPost.post.id).from(readPost).where(readPost.member.eq(reader))))
                .where(postCategory.space.eq(reader.getSpace()))
                .orderBy(noticePost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }else if(postCategoryId.equals(Long.valueOf(-1))){
            return jpaQueryFactory
                .select(Projections.constructor(NoticePostList.class, noticePost, savedPost.post.id))
                .from(noticePost)
                .join(noticePost.author, spaceMember).fetchJoin()
                .join(noticePost.postCategory, postCategory).fetchJoin()
                .join(noticePost.targets, noticeTarget).fetchJoin()
                .leftJoin(savedPost).on(noticePost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .where(noticePost.id.notIn(JPAExpressions.select(readPost.post.id).from(readPost).where(readPost.member.eq(reader))))
                .where(postCategory.space.eq(reader.getSpace()))
                .where(noticeTarget.target.eq(reader.getMemberCategory()))
                .orderBy(noticePost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }else{
            return jpaQueryFactory
                .select(Projections.constructor(NoticePostList.class, noticePost, savedPost.post.id))
                .from(noticePost)
                .join(noticePost.author, spaceMember).fetchJoin()
                .join(noticePost.postCategory, postCategory).fetchJoin()
                .leftJoin(savedPost).on(noticePost.id.eq(savedPost.post.id)).on(savedPost.member.eq(reader))
                .where(noticePost.id.notIn(JPAExpressions.select(readPost.post.id).from(readPost).where(readPost.member.eq(reader))))
                .where(noticePost.postCategory.id.eq(postCategoryId).and(postCategory.space.eq(reader.getSpace())))
                .orderBy(noticePost.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        }

    }

}
