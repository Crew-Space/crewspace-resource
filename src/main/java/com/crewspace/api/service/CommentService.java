package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.POST_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;

import com.crewspace.api.domain.comment.Comment;
import com.crewspace.api.domain.comment.CommentRepository;
import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.post.PostRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.comment.WriteCommentRequestDTO;
import com.crewspace.api.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final SpaceMemberRepository spaceMemberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void create(WriteCommentRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        Comment comment = request.toComment(spaceMember, post, request.getDescription());
        commentRepository.save(comment);
    }

}
