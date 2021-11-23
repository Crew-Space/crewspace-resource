package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.POST_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;

import com.crewspace.api.domain.memberPost.SavedPost;
import com.crewspace.api.domain.memberPost.SavedPostRepository;
import com.crewspace.api.domain.post.Post;
import com.crewspace.api.domain.post.PostRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.memberPost.SavePostRequestDTO;
import com.crewspace.api.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberPostService {

    private final SpaceMemberRepository spaceMemberRepository;
    private final PostRepository postRepository;

    private final SavedPostRepository savedPostRepository;

    @Transactional
    public void save(SavePostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        SavedPost savedPost = request.toSavedPost(post, spaceMember);
        savedPostRepository.save(savedPost);
        return;
    }
}
