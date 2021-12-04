package com.crewspace.api.domain.comment;

import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.post.QPost;
import com.crewspace.api.domain.spaceMember.QMemberCategory;
import com.crewspace.api.domain.spaceMember.QSpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.dto.res.comment.CommentListResponseDTO.CommentList;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class CommentSupportRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    QComment comment = QComment.comment;
    QPost post = QPost.post;
    QSpaceMember spaceMember = QSpaceMember.spaceMember;
    QMemberCategory memberCategory = QMemberCategory.memberCategory;


    public CommentSupportRepository(JPAQueryFactory jpaQueryFactory){
        super(Comment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<CommentList> findComments(Post post, SpaceMember reader, Pageable pageable){
        return jpaQueryFactory
            .select(Projections.constructor(CommentList.class, comment,
                    new CaseBuilder().when(comment.commentAuthor.eq(reader)).then(true).otherwise(false)))
            .from(comment)
            .join(comment.commentAuthor, spaceMember).fetchJoin()
            .join(spaceMember.memberCategory, memberCategory).fetchJoin()
            .where(comment.post.eq(post))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}
