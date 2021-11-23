package com.crewspace.api.service;

import static com.crewspace.api.constants.ExceptionCode.POST_NOT_FOUND;
import static com.crewspace.api.constants.ExceptionCode.SPACE_MEMBER_NOT_FOUND;

import com.crewspace.api.domain.memberPost.SavedPostRepository;
import com.crewspace.api.domain.post.CommunityPost;
import com.crewspace.api.domain.post.PostSupportRepository;
import com.crewspace.api.domain.spaceMember.SpaceMember;
import com.crewspace.api.domain.spaceMember.SpaceMemberRepository;
import com.crewspace.api.dto.req.post.PostRequestDTO;
import com.crewspace.api.dto.res.post.CommunityPostDetailResponseDTO;
import com.crewspace.api.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostSupportRepository postSupportRepository;
    private final SpaceMemberRepository spaceMemberRepository;
    private final SavedPostRepository savedPostRepository;

    public CommunityPostDetailResponseDTO communityDetail(PostRequestDTO request){
        SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberEmail(
                request.getSpaceId(), request.getMemberEmail())
            .orElseThrow(() -> new CustomException(SPACE_MEMBER_NOT_FOUND));

        // 작성자 정보 포함
        CommunityPost communityPost = postSupportRepository.findCommunityPost(request.getPostId())
            .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

        Boolean isAuthor = communityPost.getAuthor().getMember().getEmail().equals(request.getMemberEmail());
        Boolean isSaved = savedPostRepository.existsByPostAndMember(communityPost, spaceMember);

        return CommunityPostDetailResponseDTO.builder()
            .post(communityPost)
            .isAuthor(isAuthor)
            .isSaved(isSaved)
            .build();
    }


}
